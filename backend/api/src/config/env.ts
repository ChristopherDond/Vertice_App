import 'dotenv/config';
import { z } from 'zod';

const envSchema = z.object({
  NODE_ENV: z.enum(['development', 'test', 'production']).default('development'),
  PORT: z.coerce.number().default(3000),
  HOST: z.string().default('0.0.0.0'),
  DATABASE_HOST: z.string().default('127.0.0.1'),
  DATABASE_PORT: z.coerce.number().default(5432),
  DATABASE_USER: z.string().default('vertice_admin'),
  DATABASE_PASSWORD: z.string().default('Vertice_Db_2026_xP9#kL2!vQ'),
  DATABASE_NAME: z.string().default('vertice_db'),
  DATABASE_URL: z.string().optional(),
  JWT_SECRET: z.string().min(32).default('super_secret_vertice_jwt_key_2026_minimum_32_characters_long!'),
  JWT_EXPIRES_IN: z.string().default('30d'),
  CORS_ORIGIN: z.string().default('*'),
});

export const env = envSchema.parse(process.env);
