import { FastifyInstance } from 'fastify';
import { z } from 'zod';
import bcrypt from 'bcryptjs';
import { randomUUID } from 'node:crypto';
import { db } from '../../db/database.js';
import { AppError } from '../../plugins/error-handler.js';
import { cleanDigits } from '../../shared/formatters.js';
import { getUserDtoById } from '../../shared/user-mapper.js';

export async function authRoutes(app: FastifyInstance) {
  // 1. POST /v1/auth/login
  const loginSchema = z.object({
    credential: z.string().min(1, 'Informe seu e-mail ou CPF'),
    password: z.string().min(1, 'Informe sua senha'),
  });

  app.post('/login', async (request, reply) => {
    const { credential, password } = loginSchema.parse(request.body);
    const cleanCred = cleanDigits(credential);

    // Busca usuário por e-mail ou CPF limpo
    const user = await db
      .selectFrom('usuarios')
      .where((eb) =>
        eb.or([
          eb('email', '=', credential.trim().toLowerCase()),
          eb('cpf', '=', cleanCred.length === 11 ? cleanCred : credential),
        ])
      )
      .selectAll()
      .executeTakeFirst();

    if (!user || !user.password_hash) {
      throw new AppError(401, 'INVALID_CREDENTIALS', 'Credenciais inválidas. Verifique seu e-mail/CPF e senha.');
    }

    const passwordMatch = await bcrypt.compare(password, user.password_hash);
    if (!passwordMatch) {
      throw new AppError(401, 'INVALID_CREDENTIALS', 'Credenciais inválidas. Verifique seu e-mail/CPF e senha.');
    }

    const token = app.jwt.sign({
      userId: user.id,
      email: user.email,
      isPro: Boolean(user.is_pro),
      violetaVerified: Boolean(user.violeta_verified),
    });

    return reply.status(200).send({
      token,
      user_id: user.id,
      display_name: user.name,
      is_pro: Boolean(user.is_pro),
      violeta_verified: Boolean(user.violeta_verified),
    });
  });

  // 2. POST /v1/auth/provider (Login Social / Firebase)
  const providerSchema = z.object({
    provider: z.string().min(1),
    id_token: z.string().min(1),
  });

  app.post('/provider', async (request, reply) => {
    const { provider, id_token } = providerSchema.parse(request.body);

    // Para ambiente demo/mock ou id_token de teste
    const firebaseUid = `prov_${provider}_${id_token.substring(0, 16)}`;
    let user = await db
      .selectFrom('usuarios')
      .where('firebase_uid', '=', firebaseUid)
      .selectAll()
      .executeTakeFirst();

    if (!user) {
      const newId = `usr_${randomUUID().substring(0, 8)}`;
      const email = `social_${newId}@vertice.app`;
      user = await db
        .insertInto('usuarios')
        .values({
          id: newId,
          firebase_uid: firebaseUid,
          name: 'Usuário Conectado',
          email,
          phone: '+5511999999999',
          cpf: '00000000000',
          bio: 'Conta conectada via ' + provider,
          is_pro: false,
          violeta_verified: false,
          profile_completion: 40,
        })
        .returningAll()
        .executeTakeFirstOrThrow();

      await db.insertInto('carteira_saldo').values({ usuario_id: newId }).execute();
      await db.insertInto('protocolo_violeta').values({ usuario_id: newId }).execute();
    }

    const token = app.jwt.sign({
      userId: user.id,
      email: user.email,
      isPro: Boolean(user.is_pro),
      violetaVerified: Boolean(user.violeta_verified),
    });

    return reply.status(200).send({
      token,
      user_id: user.id,
      display_name: user.name,
      is_pro: Boolean(user.is_pro),
      violeta_verified: Boolean(user.violeta_verified),
    });
  });

  // 3. POST /v1/auth/register
  const registerSchema = z.object({
    full_name: z.string().min(2, 'Nome muito curto'),
    cpf: z.string().min(11, 'CPF inválido'),
    birth_date: z.string().optional(),
    password: z.string().min(6, 'Senha deve ter no mínimo 6 caracteres'),
    email: z.string().email('E-mail em formato inválido'),
    phone: z.string().min(8, 'Telefone inválido'),
  });

  app.post('/register', async (request, reply) => {
    const body = registerSchema.parse(request.body);
    const cleanCpf = cleanDigits(body.cpf);
    const normalizedEmail = body.email.trim().toLowerCase();

    // Verifica se já existe usuário com este e-mail ou CPF
    const existing = await db
      .selectFrom('usuarios')
      .where((eb) => eb.or([eb('email', '=', normalizedEmail), eb('cpf', '=', cleanCpf)]))
      .select(['id', 'email', 'cpf'])
      .executeTakeFirst();

    if (existing) {
      if (existing.email === normalizedEmail) {
        throw new AppError(409, 'EMAIL_ALREADY_EXISTS', 'Este endereço de e-mail já está em uso.');
      }
      throw new AppError(409, 'CPF_ALREADY_EXISTS', 'Este número de CPF já está cadastrado.');
    }

    const passwordHash = await bcrypt.hash(body.password, 10);
    const userId = `usr_${randomUUID().substring(0, 8)}`;

    const user = await db
      .insertInto('usuarios')
      .values({
        id: userId,
        name: body.full_name,
        email: normalizedEmail,
        phone: body.phone,
        cpf: cleanCpf,
        birth_date: body.birth_date || null,
        password_hash: passwordHash,
        bio: '',
        skills: [],
        is_pro: false,
        violeta_verified: false,
        profile_completion: 40,
      })
      .returningAll()
      .executeTakeFirstOrThrow();

    // Inicializa carteira de saldo zerada e registro de protocolo violeta
    await db.insertInto('carteira_saldo').values({ usuario_id: userId, disponivel: 0, pendente: 0 }).execute();
    await db.insertInto('protocolo_violeta').values({ usuario_id: userId, enabled: false }).execute();

    const token = app.jwt.sign({
      userId: user.id,
      email: user.email,
      isPro: false,
      violetaVerified: false,
    });

    return reply.status(201).send({
      token,
      user_id: user.id,
      display_name: user.name,
      is_pro: false,
      violeta_verified: false,
    });
  });

  // 4. POST /v1/auth/recovery
  const recoverySchema = z.object({
    channel: z.string().default('EMAIL'),
    contact: z.string().min(3),
  });

  app.post('/recovery', async (request, reply) => {
    const { channel, contact } = recoverySchema.parse(request.body);
    const cleanContact = contact.includes('@') ? contact.trim().toLowerCase() : cleanDigits(contact);

    // Gera código randômico de 6 dígitos
    const code = Math.floor(100000 + Math.random() * 900000).toString();
    const expiresAt = new Date(Date.now() + 15 * 60 * 1000); // 15 minutos

    await db
      .insertInto('codigos_recuperacao')
      .values({
        contato: cleanContact,
        codigo: code,
        canal: channel.toUpperCase(),
        expira_em: expiresAt,
        usado: false,
      })
      .execute();

    // Em produção integraria com serviço SMS/Email. No mock/dev logamos o código:
    request.log.info(`[RECOVERY] Código gerado para ${cleanContact}: ${code}`);

    return reply.status(200).send({
      message: 'Código de recuperação enviado com sucesso.',
    });
  });

  // 5. POST /v1/auth/reset-password
  const resetSchema = z.object({
    contact: z.string().min(3),
    code: z.string().min(4),
    new_password: z.string().min(6),
  });

  app.post('/reset-password', async (request, reply) => {
    const { contact, code, new_password } = resetSchema.parse(request.body);
    const cleanContact = contact.includes('@') ? contact.trim().toLowerCase() : cleanDigits(contact);

    // Valida código ativo
    const recoveryRecord = await db
      .selectFrom('codigos_recuperacao')
      .where('contato', '=', cleanContact)
      .where('codigo', '=', code.trim())
      .where('usado', '=', false)
      .where('expira_em', '>', new Date())
      .selectAll()
      .orderBy('created_at', 'desc')
      .executeTakeFirst();

    if (!recoveryRecord) {
      throw new AppError(400, 'INVALID_CODE', 'Código de recuperação inválido ou expirado.');
    }

    const passwordHash = await bcrypt.hash(new_password, 10);

    // Atualiza senha do usuário
    const updated = await db
      .updateTable('usuarios')
      .set({ password_hash: passwordHash, updated_at: new Date() })
      .where((eb) => eb.or([eb('email', '=', cleanContact), eb('cpf', '=', cleanContact)]))
      .executeTakeFirst();

    if (Number(updated.numUpdatedRows) === 0) {
      throw new AppError(404, 'USER_NOT_FOUND', 'Nenhum usuário encontrado para este contato.');
    }

    // Invalida o código
    await db
      .updateTable('codigos_recuperacao')
      .set({ usado: true })
      .where('id', '=', recoveryRecord.id)
      .execute();

    return reply.status(200).send({
      message: 'Senha redefinida com sucesso.',
    });
  });

  // 6. POST /v1/auth/change-password (Autenticado)
  const changePasswordSchema = z.object({
    current_password: z.string().min(1),
    new_password: z.string().min(6),
  });

  app.post('/change-password', { preHandler: [app.authenticate] }, async (request, reply) => {
    const { current_password, new_password } = changePasswordSchema.parse(request.body);
    const userId = request.user.userId;

    const user = await db
      .selectFrom('usuarios')
      .where('id', '=', userId)
      .select(['password_hash'])
      .executeTakeFirstOrThrow();

    if (user.password_hash) {
      const matches = await bcrypt.compare(current_password, user.password_hash);
      if (!matches) {
        throw new AppError(400, 'WRONG_PASSWORD', 'Senha atual incorreta.');
      }
    }

    const newHash = await bcrypt.hash(new_password, 10);
    await db
      .updateTable('usuarios')
      .set({ password_hash: newHash, updated_at: new Date() })
      .where('id', '=', userId)
      .execute();

    return reply.status(200).send({
      message: 'Senha alterada com sucesso.',
    });
  });

  // 7. POST /v1/auth/change-email (Autenticado)
  const changeEmailSchema = z.object({
    email: z.string().email(),
  });

  app.post('/change-email', { preHandler: [app.authenticate] }, async (request, reply) => {
    const { email } = changeEmailSchema.parse(request.body);
    const normalizedEmail = email.trim().toLowerCase();
    const userId = request.user.userId;

    const existing = await db
      .selectFrom('usuarios')
      .where('email', '=', normalizedEmail)
      .where('id', '!=', userId)
      .select(['id'])
      .executeTakeFirst();

    if (existing) {
      throw new AppError(409, 'EMAIL_ALREADY_EXISTS', 'Este e-mail já pertence a outra conta.');
    }

    await db
      .updateTable('usuarios')
      .set({ email: normalizedEmail, updated_at: new Date() })
      .where('id', '=', userId)
      .execute();

    const updatedUser = await getUserDtoById(userId);
    return reply.status(200).send(updatedUser);
  });

  // 8. POST /v1/auth/change-phone (Autenticado)
  const changePhoneSchema = z.object({
    phone: z.string().min(8),
    code: z.string().optional(),
  });

  app.post('/change-phone', { preHandler: [app.authenticate] }, async (request, reply) => {
    const { phone } = changePhoneSchema.parse(request.body);
    const userId = request.user.userId;

    await db
      .updateTable('usuarios')
      .set({ phone: phone.trim(), updated_at: new Date() })
      .where('id', '=', userId)
      .execute();

    const updatedUser = await getUserDtoById(userId);
    return reply.status(200).send(updatedUser);
  });

  // 9. POST /v1/auth/logout
  app.post('/logout', async (_request, reply) => {
    return reply.status(200).send({
      message: 'Sessão encerrada com sucesso.',
    });
  });
}
