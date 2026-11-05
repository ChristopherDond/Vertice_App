import { FastifyInstance } from 'fastify';
import { z } from 'zod';
import { db } from '../../db/database.js';
import { AppError } from '../../plugins/error-handler.js';

export interface FreelancerDto {
  id: string;
  name: string;
  role: string;
  area: string;
  category: string;
  match_percent: number;
  rating: number;
  services_count: number;
  availability: string;
  bio: string;
  gender: string;
  avatar_url: string | null;
  hours_worked: number;
  jobs_done: number;
  is_violeta: boolean;
  is_pro: boolean;
  skills: string[];
  hourly_rate: number;
  distance_km: number;
}

export function formatFreelancerDto(row: any): FreelancerDto {
  return {
    id: row.id,
    name: row.name,
    role: row.role,
    area: row.area,
    category: row.category,
    match_percent: Number(row.match_percent) || 90,
    rating: Number(row.rating) || 5.0,
    services_count: Number(row.services_count) || 0,
    availability: row.availability || 'Disponível',
    bio: row.bio || '',
    gender: row.gender || 'OTHER',
    avatar_url: row.avatar_url || null,
    hours_worked: Number(row.hours_worked) || 0,
    jobs_done: Number(row.jobs_done) || 0,
    is_violeta: Boolean(row.is_violeta),
    is_pro: Boolean(row.is_pro),
    skills: row.skills || [],
    hourly_rate: Number(row.hourly_rate) || 0.0,
    distance_km: Number(row.distance_km) || 1,
  };
}

export async function matchRoutes(app: FastifyInstance) {
  // 1. GET /v1/match/search
  app.get('/search', async (request, reply) => {
    const query = request.query as {
      q?: string;
      category?: string;
      violeta?: string | boolean;
      maxKm?: string | number;
      proOnly?: string | boolean;
      limit?: string | number;
      offset?: string | number;
    };

    const q = query.q ? query.q.trim() : '';
    const category = query.category ? query.category.trim().toUpperCase() : '';
    const violetaOnly = query.violeta === 'true' || query.violeta === true;
    const proOnly = query.proOnly === 'true' || query.proOnly === true;
    const maxKm = query.maxKm ? Number(query.maxKm) : 50;
    const limit = query.limit ? Math.min(Number(query.limit), 100) : 50;
    const offset = query.offset ? Number(query.offset) : 0;

    let dbQuery = db.selectFrom('freelancers').selectAll();

    if (q) {
      dbQuery = dbQuery.where((eb) =>
        eb.or([
          eb('name', 'ilike', `%${q}%`),
          eb('role', 'ilike', `%${q}%`),
          eb('bio', 'ilike', `%${q}%`),
        ])
      );
    }

    if (category && category !== 'ALL') {
      dbQuery = dbQuery.where('category', '=', category);
    }

    if (violetaOnly) {
      dbQuery = dbQuery.where('is_violeta', '=', true);
    }

    if (proOnly) {
      dbQuery = dbQuery.where('is_pro', '=', true);
    }

    if (maxKm > 0) {
      dbQuery = dbQuery.where('distance_km', '<=', maxKm);
    }

    const rows = await dbQuery
      .orderBy('rating', 'desc')
      .orderBy('match_percent', 'desc')
      .limit(limit)
      .offset(offset)
      .execute();

    const freelancers = rows.map(formatFreelancerDto);
    return reply.status(200).send(freelancers);
  });

  // 2. GET /v1/match/:id
  app.get('/:id', async (request, reply) => {
    const { id } = request.params as { id: string };

    const freelancer = await db
      .selectFrom('freelancers')
      .where('id', '=', id)
      .selectAll()
      .executeTakeFirst();

    if (!freelancer) {
      throw new AppError(404, 'FREELANCER_NOT_FOUND', 'Profissional não encontrado.');
    }

    return reply.status(200).send(formatFreelancerDto(freelancer));
  });

  // 3. POST /v1/match/requests
  const serviceRequestSchema = z.object({
    freelancer_id: z.string().min(1),
    service_type: z.string().min(1),
    date: z.string().min(1),
    time: z.string().min(1),
    budget: z.number().positive(),
    location: z.string().min(1),
    description: z.string().min(1),
    photo_urls: z.array(z.string()).default([]),
    urgency: z.enum(['NORMAL', 'ALTA', 'URGENTE']).default('NORMAL'),
  });

  app.post('/requests', { preHandler: [app.authenticate] }, async (request, reply) => {
    const body = serviceRequestSchema.parse(request.body);
    const clienteId = request.user.userId;

    const freelancer = await db
      .selectFrom('freelancers')
      .where('id', '=', body.freelancer_id)
      .select(['id'])
      .executeTakeFirst();

    if (!freelancer) {
      throw new AppError(404, 'FREELANCER_NOT_FOUND', 'Freelancer informado não existe.');
    }

    const result = await db
      .insertInto('propostas_servicos')
      .values({
        cliente_id: clienteId,
        freelancer_id: body.freelancer_id,
        service_type: body.service_type,
        budget: body.budget,
        data_agendamento: body.date,
        hora_agendamento: body.time,
        localizacao: body.location,
        descricao: body.description,
        photo_urls: body.photo_urls,
        urgencia: body.urgency,
        status: 'PENDENTE',
      })
      .returning(['id', 'status', 'created_at'])
      .executeTakeFirstOrThrow();

    return reply.status(201).send({
      message: 'Solicitação de serviço enviada com sucesso.',
      id: result.id,
      status: result.status,
    });
  });

  // 4. POST /v1/match/offers
  const serviceOfferSchema = z.object({
    category: z.string().min(1),
    title: z.string().min(1),
    description: z.string().min(1),
    price: z.number().positive(),
    city: z.string().min(1),
    availability: z.string().min(1),
  });

  app.post('/offers', { preHandler: [app.authenticate] }, async (request, reply) => {
    const body = serviceOfferSchema.parse(request.body);
    const userId = request.user.userId;

    // Localiza se usuário tem perfil de freelancer ou usa o flw da demo
    let freelancer = await db
      .selectFrom('freelancers')
      .where('usuario_id', '=', userId)
      .select(['id'])
      .executeTakeFirst();

    const freelancerId = freelancer ? freelancer.id : 'flw_camila';

    const result = await db
      .insertInto('ofertas_servicos')
      .values({
        freelancer_id: freelancerId,
        category: body.category,
        title: body.title,
        description: body.description,
        price: body.price,
        city: body.city,
        availability: body.availability,
      })
      .returning(['id', 'created_at'])
      .executeTakeFirstOrThrow();

    return reply.status(201).send({
      message: 'Oferta de serviço publicada com sucesso.',
      id: result.id,
    });
  });
}
