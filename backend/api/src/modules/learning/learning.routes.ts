import { FastifyInstance } from 'fastify';
import { db } from '../../db/database.js';

export async function learningRoutes(app: FastifyInstance) {
  // 1. GET /v1/learning/modules
  app.get('/modules', async (_request, reply) => {
    // 1. Busca todos os módulos ordenados
    const modules = await db
      .selectFrom('trilha_modulos')
      .selectAll()
      .orderBy('ordem', 'asc')
      .execute();

    // 2. Busca todas as lições ordenadas
    const lessons = await db
      .selectFrom('trilha_licoes')
      .selectAll()
      .orderBy('ordem', 'asc')
      .execute();

    // 3. Busca todos os cards ordenados
    const cards = await db
      .selectFrom('trilha_cards')
      .selectAll()
      .orderBy('ordem', 'asc')
      .execute();

    // Mapeia hierarquicamente: Modulo -> Lições -> Cards
    const result = modules.map((m) => {
      const moduleLessons = lessons
        .filter((l) => l.modulo_id === m.id)
        .map((l) => {
          const lessonCards = cards
            .filter((c) => c.licao_id === l.id)
            .map((c) => ({
              heading: c.heading,
              body: c.body,
              tip: c.tip,
            }));

          return {
            id: l.id,
            title: l.title,
            cards: lessonCards,
          };
        });

      return {
        id: m.id,
        title: m.title,
        lessons: moduleLessons,
      };
    });

    return reply.status(200).send(result);
  });
}
