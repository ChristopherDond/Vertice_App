import { describe, it, expect, beforeAll, afterAll } from 'vitest';
import { FastifyInstance } from 'fastify';
import { buildApp } from '../src/app.js';
import { pool } from '../src/db/database.js';

describe('Vértice API — Testes de Integração End-to-End', () => {
  let app: FastifyInstance;
  let authToken: string;

  beforeAll(async () => {
    app = await buildApp();
  });

  afterAll(async () => {
    await app.close();
    await pool.end();
  });

  it('1. GET /v1/learning/modules deve retornar 5 módulos com lições e cards estruturados', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/learning/modules',
    });

    expect(res.statusCode).toBe(200);
    const modules = JSON.parse(res.body);
    expect(modules.length).toBe(5);
    expect(modules[0].id).toBe('m1');
    expect(modules[0].title).toBe('Segurança Pessoal');
    expect(modules[0].lessons.length).toBe(3);
    expect(modules[0].lessons[0].id).toBe('s1');
    expect(modules[0].lessons[0].cards.length).toBeGreaterThanOrEqual(1);
  });

  it('2. GET /v1/plans deve retornar o catálogo com o Plano Pro', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/plans',
    });

    expect(res.statusCode).toBe(200);
    const plans = JSON.parse(res.body);
    expect(plans.length).toBeGreaterThanOrEqual(1);
    const proPlan = plans.find((p: any) => p.id === 'plan_pro_monthly');
    expect(proPlan).toBeDefined();
    expect(proPlan.name).toBe('Vértice Pro');
    expect(proPlan.monthly_price).toBe(19.99);
    expect(proPlan.benefits.length).toBe(5);
  });

  it('3. GET /v1/match/search deve listar freelancers com dados e scores de match', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/match/search',
    });

    expect(res.statusCode).toBe(200);
    const freelancers = JSON.parse(res.body);
    expect(freelancers.length).toBe(12);
    expect(freelancers[0]).toHaveProperty('id');
    expect(freelancers[0]).toHaveProperty('match_percent');
    expect(freelancers[0]).toHaveProperty('rating');
  });

  it('4. GET /v1/match/search com filtro violeta=true e category=SERVICES', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/match/search?category=SERVICES&violeta=true',
    });

    expect(res.statusCode).toBe(200);
    const freelancers = JSON.parse(res.body);
    expect(freelancers.length).toBeGreaterThan(0);
    freelancers.forEach((f: any) => {
      expect(f.category).toBe('SERVICES');
      expect(f.is_violeta).toBe(true);
    });
  });

  it('5. GET /v1/match/:id com ID existente retorna detalhes completos', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/match/flw_camila',
    });

    expect(res.statusCode).toBe(200);
    const f = JSON.parse(res.body);
    expect(f.id).toBe('flw_camila');
    expect(f.name).toBe('Camila Ferreira');
    expect(f.category).toBe('CONSULTING');
    expect(f.hourly_rate).toBe(180);
    expect(f.is_violeta).toBe(true);
    expect(f.is_pro).toBe(true);
  });

  it('6. POST /v1/auth/login com credenciais de Ana Silva gera SessionDto e JWT', async () => {
    const res = await app.inject({
      method: 'POST',
      url: '/v1/auth/login',
      payload: {
        credential: 'ana.silva@vertice.app',
        password: 'senha123',
      },
    });

    expect(res.statusCode).toBe(200);
    const session = JSON.parse(res.body);
    expect(session.user_id).toBe('usr_ana');
    expect(session.display_name).toBe('Ana Silva');
    expect(session.is_pro).toBe(true);
    expect(session.violeta_verified).toBe(true);
    expect(session.token).toBeDefined();

    authToken = session.token;
  });

  it('7. GET /v1/profile/me com Bearer token retorna UserDto completo com CPF mascarado', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/profile/me',
      headers: {
        authorization: `Bearer ${authToken}`,
      },
    });

    expect(res.statusCode).toBe(200);
    const user = JSON.parse(res.body);
    expect(user.id).toBe('usr_ana');
    expect(user.email).toBe('ana.silva@vertice.app');
    expect(user.cpf_masked).toBe('***.982.247-**');
    expect(user.is_pro).toBe(true);
    expect(user.violeta_verified).toBe(true);
    expect(user.address).toBeDefined();
    expect(user.address.city).toBe('São Paulo');
  });

  it('8. GET /v1/profile/trilha e PUT /v1/profile/trilha/:lessonId gerencia progresso', async () => {
    // Marca s1 como concluída
    const putRes = await app.inject({
      method: 'PUT',
      url: '/v1/profile/trilha/s1?done=true',
      headers: {
        authorization: `Bearer ${authToken}`,
      },
    });
    expect(putRes.statusCode).toBe(200);

    // Consulta lições concluídas
    const getRes = await app.inject({
      method: 'GET',
      url: '/v1/profile/trilha',
      headers: {
        authorization: `Bearer ${authToken}`,
      },
    });
    expect(getRes.statusCode).toBe(200);
    const doneList = JSON.parse(getRes.body);
    expect(doneList).toContain('s1');
  });

  it('9. GET /v1/profile/violeta retorna status do Protocolo Violeta', async () => {
    const res = await app.inject({
      method: 'GET',
      url: '/v1/profile/violeta',
      headers: {
        authorization: `Bearer ${authToken}`,
      },
    });

    expect(res.statusCode).toBe(200);
    const violeta = JSON.parse(res.body);
    expect(violeta.enabled).toBe(true);
    expect(violeta.identity_verified).toBe(true);
    expect(violeta.phone_verified).toBe(true);
  });

  it('10. GET /v1/finance/balance e /v1/finance/subscription retornam dados financeiros', async () => {
    const balanceRes = await app.inject({
      method: 'GET',
      url: '/v1/finance/balance',
      headers: {
        authorization: `Bearer ${authToken}`,
      },
    });
    expect(balanceRes.statusCode).toBe(200);
    const balance = JSON.parse(balanceRes.body);
    expect(balance.currency).toBe('BRL');

    const subRes = await app.inject({
      method: 'GET',
      url: '/v1/finance/subscription',
      headers: {
        authorization: `Bearer ${authToken}`,
      },
    });
    expect(subRes.statusCode).toBe(200);
    const sub = JSON.parse(subRes.body);
    expect(sub.active).toBe(true);
    expect(sub.plan_name).toBe('Vértice Pro');
  });
});
