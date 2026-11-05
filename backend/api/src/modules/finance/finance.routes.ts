import { FastifyInstance } from 'fastify';
import { z } from 'zod';
import { randomUUID } from 'node:crypto';
import { db } from '../../db/database.js';
import { AppError } from '../../plugins/error-handler.js';

export async function financeRoutes(app: FastifyInstance) {
  // Rotas financeiras do usuário autenticado
  app.addHook('preHandler', app.authenticate);

  // 1. GET /v1/finance/balance
  app.get('/balance', async (request, reply) => {
    const userId = request.user.userId;

    let balance = await db
      .selectFrom('carteira_saldo')
      .where('usuario_id', '=', userId)
      .selectAll()
      .executeTakeFirst();

    if (!balance) {
      balance = await db
        .insertInto('carteira_saldo')
        .values({ usuario_id: userId, disponivel: 0.0, pendente: 0.0, moeda: 'BRL' })
        .returningAll()
        .executeTakeFirstOrThrow();
    }

    return reply.status(200).send({
      available: Number(balance.disponivel) || 0.0,
      pending: Number(balance.pendente) || 0.0,
      currency: balance.moeda || 'BRL',
    });
  });

  // 2. GET /v1/finance/transactions
  app.get('/transactions', async (request, reply) => {
    const userId = request.user.userId;

    const rows = await db
      .selectFrom('transacoes_financeiras')
      .where('usuario_id', '=', userId)
      .selectAll()
      .orderBy('created_at', 'desc')
      .execute();

    const transactions = rows.map((t) => ({
      id: t.id,
      client_name: t.client_name,
      service: t.service,
      location: t.location,
      amount: Number(t.amount) || 0.0,
      date: t.data_transacao,
      status: t.status,
      withdrawable_at: t.withdrawable_at ? t.withdrawable_at.toISOString() : null,
    }));

    return reply.status(200).send(transactions);
  });

  // 3. GET /v1/finance/cards
  app.get('/cards', async (request, reply) => {
    const userId = request.user.userId;

    const rows = await db
      .selectFrom('cartoes_pagamento')
      .where('usuario_id', '=', userId)
      .selectAll()
      .orderBy('is_primary', 'desc')
      .orderBy('created_at', 'desc')
      .execute();

    const cards = rows.map((c) => ({
      id: c.id,
      brand: c.brand,
      last4: c.last4,
      holder_name: c.holder_name,
      expiry: c.expiry,
      label: c.label,
      is_primary: Boolean(c.is_primary),
    }));

    return reply.status(200).send(cards);
  });

  // 4. POST /v1/finance/cards
  const cardRequestSchema = z.object({
    number: z.string().min(13),
    holder_name: z.string().min(2),
    expiry: z.string().min(4),
    cvv: z.string().min(3),
    document: z.string().optional(),
    bank: z.string().optional(),
  });

  app.post('/cards', async (request, reply) => {
    const body = cardRequestSchema.parse(request.body);
    const userId = request.user.userId;

    const cleanNumber = body.number.replace(/\D/g, '');
    const last4 = cleanNumber.slice(-4);

    let brand = 'Mastercard';
    if (cleanNumber.startsWith('4')) brand = 'Visa';
    else if (cleanNumber.startsWith('5')) brand = 'Mastercard';
    else if (cleanNumber.startsWith('3')) brand = 'Amex';
    else if (cleanNumber.startsWith('6')) brand = 'Elo';

    const cardId = `crd_${randomUUID().substring(0, 8)}`;

    const card = await db
      .insertInto('cartoes_pagamento')
      .values({
        id: cardId,
        usuario_id: userId,
        brand,
        last4,
        holder_name: body.holder_name.toUpperCase(),
        expiry: body.expiry,
        label: `${brand} •••• ${last4}`,
        is_primary: true,
      })
      .returningAll()
      .executeTakeFirstOrThrow();

    return reply.status(201).send({
      id: card.id,
      brand: card.brand,
      last4: card.last4,
      holder_name: card.holder_name,
      expiry: card.expiry,
      label: card.label,
      is_primary: Boolean(card.is_primary),
    });
  });

  // 5. DELETE /v1/finance/cards/:id
  app.delete('/cards/:id', async (request, reply) => {
    const { id } = request.params as { id: string };
    const userId = request.user.userId;

    const result = await db
      .deleteFrom('cartoes_pagamento')
      .where('id', '=', id)
      .where('usuario_id', '=', userId)
      .executeTakeFirst();

    if (Number(result.numDeletedRows) === 0) {
      throw new AppError(404, 'CARD_NOT_FOUND', 'Cartão não encontrado.');
    }

    return reply.status(200).send({
      message: 'Cartão removido com sucesso.',
    });
  });

  // Helper para obter assinatura do usuário
  async function getUserSubscription(userId: string) {
    const sub = await db
      .selectFrom('assinaturas')
      .innerJoin('planos', 'planos.id', 'assinaturas.plano_id')
      .where('assinaturas.usuario_id', '=', userId)
      .select([
        'assinaturas.active',
        'assinaturas.next_charge_date',
        'assinaturas.auto_renew',
        'assinaturas.auto_renew_method',
        'assinaturas.status',
        'planos.name as plan_name',
        'planos.monthly_price',
      ])
      .executeTakeFirst();

    if (!sub) {
      return {
        active: false,
        plan_name: 'Vértice Free',
        monthly_price: 0.0,
        next_charge_date: '',
        auto_renew: false,
        auto_renew_method: 'PIX',
        status: 'UP_TO_DATE',
      };
    }

    return {
      active: Boolean(sub.active),
      plan_name: sub.plan_name,
      monthly_price: Number(sub.monthly_price) || 19.99,
      next_charge_date: sub.next_charge_date || '',
      auto_renew: Boolean(sub.auto_renew),
      auto_renew_method: sub.auto_renew_method,
      status: sub.status,
    };
  }

  // 6. GET /v1/finance/subscription
  app.get('/subscription', async (request, reply) => {
    const userId = request.user.userId;
    const subscription = await getUserSubscription(userId);
    return reply.status(200).send(subscription);
  });

  // 7. PUT /v1/finance/subscription/auto-renew
  const autoRenewSchema = z.object({
    method: z.enum(['PIX', 'CARD']),
    enabled: z.boolean(),
  });

  app.put('/subscription/auto-renew', async (request, reply) => {
    const body = autoRenewSchema.parse(request.body);
    const userId = request.user.userId;

    await db
      .updateTable('assinaturas')
      .set({
        auto_renew: body.enabled,
        auto_renew_method: body.method,
        updated_at: new Date(),
      })
      .where('usuario_id', '=', userId)
      .execute();

    const subscription = await getUserSubscription(userId);
    return reply.status(200).send(subscription);
  });

  // 8. DELETE /v1/finance/subscription
  app.delete('/subscription', async (request, reply) => {
    const userId = request.user.userId;

    await db
      .updateTable('assinaturas')
      .set({
        active: false,
        status: 'CANCELLED',
        auto_renew: false,
        updated_at: new Date(),
      })
      .where('usuario_id', '=', userId)
      .execute();

    await db
      .updateTable('usuarios')
      .set({ is_pro: false, updated_at: new Date() })
      .where('id', '=', userId)
      .execute();

    return reply.status(200).send({
      message: 'Assinatura cancelada com sucesso.',
    });
  });
}

// Rotas de Planos e Assinatura (SubscriptionApi)
export async function subscriptionRoutes(app: FastifyInstance) {
  // 1. GET /v1/plans
  app.get('/plans', async (_request, reply) => {
    const rows = await db
      .selectFrom('planos')
      .where('active', '=', true)
      .selectAll()
      .execute();

    const plans = rows.map((p) => ({
      id: p.id,
      name: p.name,
      monthly_price: Number(p.monthly_price),
      benefits: p.benefits,
    }));

    return reply.status(200).send(plans);
  });

  // 2. POST /v1/subscriptions
  const subscribeSchema = z.object({
    plan_id: z.string().min(1),
    payment_method: z.string().default('PIX'),
  });

  app.post('/subscriptions', { preHandler: [app.authenticate] }, async (request, reply) => {
    const body = subscribeSchema.parse(request.body);
    const userId = request.user.userId;

    const plan = await db
      .selectFrom('planos')
      .where('id', '=', body.plan_id)
      .selectAll()
      .executeTakeFirst();

    if (!plan) {
      throw new AppError(404, 'PLAN_NOT_FOUND', 'Plano selecionado não existe.');
    }

    const nextChargeDate = new Date();
    nextChargeDate.setDate(nextChargeDate.getDate() + 30);
    const nextChargeStr = nextChargeDate.toISOString().split('T')[0];

    const existingSub = await db
      .selectFrom('assinaturas')
      .where('usuario_id', '=', userId)
      .select(['id'])
      .executeTakeFirst();

    const method = body.payment_method.toUpperCase() === 'CARD' ? 'CARD' : 'PIX';

    if (existingSub) {
      await db
        .updateTable('assinaturas')
        .set({
          plano_id: plan.id,
          active: true,
          status: 'UP_TO_DATE',
          next_charge_date: nextChargeStr,
          auto_renew: true,
          auto_renew_method: method,
          updated_at: new Date(),
        })
        .where('id', '=', existingSub.id)
        .execute();
    } else {
      await db
        .insertInto('assinaturas')
        .values({
          usuario_id: userId,
          plano_id: plan.id,
          active: true,
          status: 'UP_TO_DATE',
          next_charge_date: nextChargeStr,
          auto_renew: true,
          auto_renew_method: method,
        })
        .execute();
    }

    // Ativa Pro no usuário
    await db
      .updateTable('usuarios')
      .set({ is_pro: true, updated_at: new Date() })
      .where('id', '=', userId)
      .execute();

    return reply.status(200).send({
      active: true,
      plan_name: plan.name,
      monthly_price: Number(plan.monthly_price),
      next_charge_date: nextChargeStr,
      auto_renew: true,
      auto_renew_method: method,
      status: 'UP_TO_DATE',
    });
  });
}
