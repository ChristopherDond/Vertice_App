import { describe, it, expect } from 'vitest';
import { buildApp } from '../src/app.js';

describe('Healthcheck & Error Handler', () => {
  it('GET /health deve retornar status ok', async () => {
    const app = await buildApp();
    const response = await app.inject({
      method: 'GET',
      url: '/health',
    });

    expect(response.statusCode).toBe(200);
    const body = JSON.parse(response.body);
    expect(body.status).toBe('ok');
    expect(body.service).toBe('vertice-api');
    expect(body.timestamp).toBeDefined();
    await app.close();
  });

  it('GET /rota-inexistente deve retornar 404 padronizado em ApiErrorDto', async () => {
    const app = await buildApp();
    const response = await app.inject({
      method: 'GET',
      url: '/rota-inexistente',
    });

    expect(response.statusCode).toBe(404);
    const body = JSON.parse(response.body);
    expect(body.code).toBe('ROUTE_NOT_FOUND');
    expect(body.message).toContain('não encontrada');
    await app.close();
  });
});
