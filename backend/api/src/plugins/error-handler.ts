import { FastifyError, FastifyInstance, FastifyReply, FastifyRequest } from 'fastify';
import { ZodError } from 'zod';

export interface ApiErrorDto {
  code: string;
  message: string;
}

export class AppError extends Error {
  constructor(
    public readonly statusCode: number,
    public readonly code: string,
    message: string
  ) {
    super(message);
    this.name = 'AppError';
  }
}

export function setupErrorHandler(app: FastifyInstance) {
  // Not Found Handler
  app.setNotFoundHandler((request: FastifyRequest, reply: FastifyReply) => {
    const errorResponse: ApiErrorDto = {
      code: 'ROUTE_NOT_FOUND',
      message: `Rota ${request.method} ${request.url} não encontrada`,
    };
    reply.status(404).send(errorResponse);
  });

  // Global Error Handler
  app.setErrorHandler((error: FastifyError | Error, request: FastifyRequest, reply: FastifyReply) => {
    request.log.error(error);

    // 1. Erro de validação Zod
    if (error instanceof ZodError) {
      const issue = error.issues[0];
      const field = issue.path.join('.');
      const errorResponse: ApiErrorDto = {
        code: 'VALIDATION_ERROR',
        message: field ? `Campo inválido '${field}': ${issue.message}` : issue.message,
      };
      return reply.status(400).send(errorResponse);
    }

    // 2. Erros customizados da aplicação
    if (error instanceof AppError) {
      const errorResponse: ApiErrorDto = {
        code: error.code,
        message: error.message,
      };
      return reply.status(error.statusCode).send(errorResponse);
    }

    // 3. Erro de autenticação Fastify JWT
    if ('statusCode' in error && error.statusCode === 401) {
      const errorResponse: ApiErrorDto = {
        code: 'UNAUTHORIZED',
        message: 'Token de autenticação ausente, inválido ou expirado',
      };
      return reply.status(401).send(errorResponse);
    }

    // 4. Erro de validação do Fastify Schema
    if ('validation' in error) {
      const errorResponse: ApiErrorDto = {
        code: 'BAD_REQUEST',
        message: error.message,
      };
      return reply.status(400).send(errorResponse);
    }

    // 5. Erro interno não tratado
    const statusCode = ('statusCode' in error && typeof error.statusCode === 'number') ? error.statusCode : 500;
    const errorResponse: ApiErrorDto = {
      code: 'INTERNAL_SERVER_ERROR',
      message: statusCode === 500 ? 'Ocorreu um erro interno no servidor' : error.message,
    };
    return reply.status(statusCode).send(errorResponse);
  });
}
