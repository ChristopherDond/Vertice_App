import { FastifyInstance, FastifyReply, FastifyRequest } from 'fastify';
import { AppError } from './error-handler.js';

export interface JwtPayload {
  userId: string;
  email: string;
  isPro: boolean;
  violetaVerified: boolean;
}

declare module 'fastify' {
  interface FastifyInstance {
    authenticate: (request: FastifyRequest, reply: FastifyReply) => Promise<void>;
  }
}

declare module '@fastify/jwt' {
  interface FastifyJWT {
    payload: JwtPayload;
    user: JwtPayload;
  }
}

export function setupAuthPlugin(app: FastifyInstance) {
  app.decorate('authenticate', async (request: FastifyRequest, reply: FastifyReply) => {
    try {
      await request.jwtVerify();
    } catch (err) {
      throw new AppError(401, 'UNAUTHORIZED', 'Token de autenticação inválido ou expirado');
    }
  });
}
