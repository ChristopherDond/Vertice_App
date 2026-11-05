import { Kysely, PostgresDialect } from 'kysely';
import pg from 'pg';
import { Database } from './schema.js';
import { env } from '../config/env.js';

const { Pool } = pg;

export const pool = new Pool(
  env.DATABASE_URL
    ? { connectionString: env.DATABASE_URL, max: 10, idleTimeoutMillis: 30000, connectionTimeoutMillis: 5000 }
    : {
        host: env.DATABASE_HOST,
        port: env.DATABASE_PORT,
        user: env.DATABASE_USER,
        password: env.DATABASE_PASSWORD,
        database: env.DATABASE_NAME,
        max: 10,
        idleTimeoutMillis: 30000,
        connectionTimeoutMillis: 5000,
      }
);

export const db = new Kysely<Database>({
  dialect: new PostgresDialect({
    pool,
  }),
});
