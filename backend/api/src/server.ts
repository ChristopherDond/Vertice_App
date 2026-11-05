import { buildApp } from './app.js';
import { env } from './config/env.js';

async function main() {
  const app = await buildApp();

  try {
    const address = await app.listen({
      port: env.PORT,
      host: env.HOST,
    });
    app.log.info(`🚀 Vértice API rodando em ${address}`);
  } catch (err) {
    app.log.error(err);
    process.exit(1);
  }
}

main();
