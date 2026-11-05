import { FastifyInstance } from 'fastify';
import { z } from 'zod';
import { db } from '../../db/database.js';
import { AppError } from '../../plugins/error-handler.js';
import { getUserDtoById, UserDto } from '../../shared/user-mapper.js';

export async function profileRoutes(app: FastifyInstance) {
  // Todas as rotas de perfil exigem autenticação
  app.addHook('preHandler', app.authenticate);

  // 1. GET /v1/profile/me
  app.get('/me', async (request, reply) => {
    const userId = request.user.userId;
    const user = await getUserDtoById(userId);

    if (!user) {
      throw new AppError(404, 'USER_NOT_FOUND', 'Usuário não encontrado.');
    }

    return reply.status(200).send(user);
  });

  // 2. PUT /v1/profile/me
  const updateProfileSchema = z.object({
    full_name: z.string().min(2),
    bio: z.string().default(''),
    skills: z.array(z.string()).default([]),
    email: z.string().email(),
    phone: z.string().min(8),
    address: z
      .object({
        cep: z.string(),
        street: z.string(),
        number: z.string(),
        complement: z.string().optional().default(''),
        district: z.string(),
        city: z.string(),
        state: z.string(),
      })
      .nullable()
      .optional(),
    notifications_enabled: z.boolean().default(true),
  });

  app.put('/me', async (request, reply) => {
    const body = updateProfileSchema.parse(request.body);
    const userId = request.user.userId;

    // Atualiza tabela usuarios
    await db
      .updateTable('usuarios')
      .set({
        name: body.full_name,
        bio: body.bio,
        skills: body.skills,
        email: body.email.trim().toLowerCase(),
        phone: body.phone.trim(),
        notifications_enabled: body.notifications_enabled,
        updated_at: new Date(),
      })
      .where('id', '=', userId)
      .execute();

    // Se endereço fornecido, atualiza ou insere na tabela enderecos
    if (body.address) {
      const existingAddress = await db
        .selectFrom('enderecos')
        .where('usuario_id', '=', userId)
        .select(['id'])
        .executeTakeFirst();

      if (existingAddress) {
        await db
          .updateTable('enderecos')
          .set({
            cep: body.address.cep,
            rua: body.address.street,
            numero: body.address.number,
            complemento: body.address.complement || '',
            district: body.address.district,
            cidade: body.address.city,
            estado: body.address.state,
            updated_at: new Date(),
          })
          .where('id', '=', existingAddress.id)
          .execute();
      } else {
        await db
          .insertInto('enderecos')
          .values({
            usuario_id: userId,
            cep: body.address.cep,
            rua: body.address.street,
            numero: body.address.number,
            complemento: body.address.complement || '',
            district: body.address.district,
            cidade: body.address.city,
            estado: body.address.state,
          })
          .execute();
      }
    }

    const updatedUser = await getUserDtoById(userId);
    return reply.status(200).send(updatedUser);
  });

  // 3. GET /v1/profile/trilha -> Retorna lista de IDs de lições concluídas
  app.get('/trilha', async (request, reply) => {
    const userId = request.user.userId;

    const completedLessons = await db
      .selectFrom('trilha_progresso_usuario')
      .where('usuario_id', '=', userId)
      .where('done', '=', true)
      .select(['licao_id'])
      .execute();

    const lessonIds = completedLessons.map((l) => l.licao_id);
    return reply.status(200).send(lessonIds);
  });

  // 4. PUT /v1/profile/trilha/:lessonId?done=true/false
  app.put('/trilha/:lessonId', async (request, reply) => {
    const { lessonId } = request.params as { lessonId: string };
    const query = request.query as { done?: string | boolean };
    const done = query.done === 'true' || query.done === true;
    const userId = request.user.userId;

    const existing = await db
      .selectFrom('trilha_progresso_usuario')
      .where('usuario_id', '=', userId)
      .where('licao_id', '=', lessonId)
      .select(['done'])
      .executeTakeFirst();

    if (existing) {
      await db
        .updateTable('trilha_progresso_usuario')
        .set({
          done,
          completed_at: done ? new Date() : null,
        })
        .where('usuario_id', '=', userId)
        .where('licao_id', '=', lessonId)
        .execute();
    } else {
      await db
        .insertInto('trilha_progresso_usuario')
        .values({
          usuario_id: userId,
          licao_id: lessonId,
          done,
          completed_at: done ? new Date() : null,
        })
        .execute();
    }

    return reply.status(200).send({
      message: 'Progresso da lição atualizado.',
      lesson_id: lessonId,
      done,
    });
  });

  // Helper para buscar status do Violeta
  async function getVioletaStatus(userId: string) {
    let violeta = await db
      .selectFrom('protocolo_violeta')
      .where('usuario_id', '=', userId)
      .selectAll()
      .executeTakeFirst();

    if (!violeta) {
      violeta = await db
        .insertInto('protocolo_violeta')
        .values({ usuario_id: userId, enabled: false })
        .returningAll()
        .executeTakeFirstOrThrow();
    }

    return {
      enabled: Boolean(violeta.enabled),
      identity_verified: Boolean(violeta.identity_verified),
      phone_verified: Boolean(violeta.phone_verified),
      identity_verified_at: violeta.identity_verified_at ? violeta.identity_verified_at.toISOString() : null,
      phone_verified_at: violeta.phone_verified_at ? violeta.phone_verified_at.toISOString() : null,
      safe_route_enabled: Boolean(violeta.safe_route_enabled),
      emergency_button_enabled: Boolean(violeta.emergency_button_enabled),
    };
  }

  // 5. GET /v1/profile/violeta
  app.get('/violeta', async (request, reply) => {
    const userId = request.user.userId;
    const status = await getVioletaStatus(userId);
    return reply.status(200).send(status);
  });

  // 6. PUT /v1/profile/violeta?enabled=true/false
  app.put('/violeta', async (request, reply) => {
    const userId = request.user.userId;
    const query = request.query as { enabled?: string | boolean };
    const enabled = query.enabled === 'true' || query.enabled === true;

    await db
      .updateTable('protocolo_violeta')
      .set({
        enabled,
        safe_route_enabled: enabled,
        emergency_button_enabled: enabled,
        updated_at: new Date(),
      })
      .where('usuario_id', '=', userId)
      .execute();

    const status = await getVioletaStatus(userId);
    return reply.status(200).send(status);
  });

  // 7. POST /v1/profile/violeta/verify?step=DOCS|PHONE|SELFIE&reference=...
  app.post('/violeta/verify', async (request, reply) => {
    const userId = request.user.userId;
    const query = request.query as { step?: string; reference?: string };
    const step = (query.step || 'DOCS').toUpperCase();

    const updateFields: Record<string, any> = { updated_at: new Date() };

    if (step === 'PHONE') {
      updateFields.phone_verified = true;
      updateFields.phone_verified_at = new Date();
    } else {
      // DOCS ou SELFIE
      updateFields.identity_verified = true;
      updateFields.identity_verified_at = new Date();
      // Atualiza também na tabela usuarios
      await db
        .updateTable('usuarios')
        .set({ violeta_verified: true, updated_at: new Date() })
        .where('id', '=', userId)
        .execute();
    }

    await db
      .updateTable('protocolo_violeta')
      .set(updateFields)
      .where('usuario_id', '=', userId)
      .execute();

    const status = await getVioletaStatus(userId);
    return reply.status(200).send(status);
  });
}
