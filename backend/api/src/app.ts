import fastify, { FastifyInstance } from 'fastify';
import cors from '@fastify/cors';
import helmet from '@fastify/helmet';
import jwt from '@fastify/jwt';
import { env } from './config/env.js';
import { setupErrorHandler } from './plugins/error-handler.js';
import { setupAuthPlugin } from './plugins/auth.js';
import { authRoutes } from './modules/auth/auth.routes.js';
import { profileRoutes } from './modules/profile/profile.routes.js';
import { matchRoutes } from './modules/match/match.routes.js';
import { financeRoutes, subscriptionRoutes } from './modules/finance/finance.routes.js';
import { learningRoutes } from './modules/learning/learning.routes.js';

export async function buildApp(): Promise<FastifyInstance> {
  const app = fastify({
    logger: env.NODE_ENV !== 'test',
  });

  // Plugins de Segurança e Cross-Origin
  await app.register(cors, {
    origin: env.CORS_ORIGIN,
    methods: ['GET', 'POST', 'PUT', 'PATCH', 'DELETE', 'OPTIONS'],
  });

  await app.register(helmet, {
    contentSecurityPolicy: false,
  });

  // Autenticação JWT
  await app.register(jwt, {
    secret: env.JWT_SECRET,
    sign: {
      expiresIn: env.JWT_EXPIRES_IN,
    },
  });

  // Decorador de autenticação (app.authenticate)
  setupAuthPlugin(app);

  // Tratamento de Erros e 404 padronizado em ApiErrorDto
  setupErrorHandler(app);

  // Healthcheck endpoints
  const healthHandler = async () => ({
    status: 'ok',
    service: 'vertice-api',
    version: '1.0.0',
    timestamp: new Date().toISOString(),
  });

  app.get('/health', healthHandler);
  app.get('/v1/health', healthHandler);

  // Módulos de Rotas (v1)
  await app.register(authRoutes, { prefix: '/v1/auth' });
  await app.register(profileRoutes, { prefix: '/v1/profile' });
  await app.register(matchRoutes, { prefix: '/v1/match' });
  await app.register(financeRoutes, { prefix: '/v1/finance' });
  await app.register(subscriptionRoutes, { prefix: '/v1' });
  await app.register(learningRoutes, { prefix: '/v1/learning' });

  return app;
}
