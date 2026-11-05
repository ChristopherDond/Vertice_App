-- =============================================================================
-- VÉRTICE APP — SCHEMA RELACIONAL DO BANCO DE DADOS (POSTGRESQL 16)
-- Arquivo: 01_schema.sql
-- =============================================================================

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- 1. TABELA DE USUÁRIOS
CREATE TABLE IF NOT EXISTS usuarios (
    id VARCHAR(64) PRIMARY KEY,
    firebase_uid VARCHAR(128) UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone VARCHAR(50) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    birth_date DATE,
    password_hash VARCHAR(255),
    bio TEXT NOT NULL DEFAULT '',
    skills TEXT[] NOT NULL DEFAULT '{}',
    avatar_url TEXT,
    is_pro BOOLEAN NOT NULL DEFAULT FALSE,
    violeta_verified BOOLEAN NOT NULL DEFAULT FALSE,
    profile_completion INT NOT NULL DEFAULT 0,
    rating NUMERIC(3,2) NOT NULL DEFAULT 0.0,
    completed_services INT NOT NULL DEFAULT 0,
    notifications_enabled BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 2. TABELA DE ENDEREÇOS
CREATE TABLE IF NOT EXISTS enderecos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id VARCHAR(64) NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    cep VARCHAR(10) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero VARCHAR(50) NOT NULL,
    complemento VARCHAR(255) NOT NULL DEFAULT '',
    district VARCHAR(100) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    latitude NUMERIC(10,7),
    longitude NUMERIC(10,7),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 3. TABELA DE FREELANCERS / PRESTADORES DO MATCH
CREATE TABLE IF NOT EXISTS freelancers (
    id VARCHAR(64) PRIMARY KEY,
    usuario_id VARCHAR(64) REFERENCES usuarios(id) ON DELETE SET NULL,
    name VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL,
    area VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    match_percent INT NOT NULL DEFAULT 90,
    rating NUMERIC(3,2) NOT NULL DEFAULT 5.0,
    services_count INT NOT NULL DEFAULT 0,
    availability VARCHAR(100) NOT NULL DEFAULT 'Disponível',
    bio TEXT NOT NULL DEFAULT '',
    gender VARCHAR(20) NOT NULL DEFAULT 'OTHER',
    avatar_url TEXT,
    hours_worked INT NOT NULL DEFAULT 0,
    jobs_done INT NOT NULL DEFAULT 0,
    is_violeta BOOLEAN NOT NULL DEFAULT FALSE,
    is_pro BOOLEAN NOT NULL DEFAULT FALSE,
    skills TEXT[] NOT NULL DEFAULT '{}',
    hourly_rate NUMERIC(10,2) NOT NULL DEFAULT 0.0,
    distance_km INT NOT NULL DEFAULT 1,
    latitude NUMERIC(10,7),
    longitude NUMERIC(10,7),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 4. TABELA DE AVALIAÇÕES (REVIEWS)
CREATE TABLE IF NOT EXISTS avaliacoes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    freelancer_id VARCHAR(64) NOT NULL REFERENCES freelancers(id) ON DELETE CASCADE,
    autor_id VARCHAR(64) REFERENCES usuarios(id) ON DELETE SET NULL,
    autor_nome VARCHAR(255) NOT NULL,
    nota NUMERIC(2,1) NOT NULL CHECK (nota >= 1.0 AND nota <= 5.0),
    comentario TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 5. TABELA DE PROPOSTAS E PEDIDOS DE SERVIÇO
CREATE TABLE IF NOT EXISTS propostas_servicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    cliente_id VARCHAR(64) NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    freelancer_id VARCHAR(64) NOT NULL REFERENCES freelancers(id) ON DELETE CASCADE,
    service_type VARCHAR(100) NOT NULL,
    budget NUMERIC(10,2) NOT NULL,
    data_agendamento DATE NOT NULL,
    hora_agendamento TIME NOT NULL,
    localizacao TEXT NOT NULL,
    descricao TEXT NOT NULL,
    photo_urls TEXT[] NOT NULL DEFAULT '{}',
    urgencia VARCHAR(20) NOT NULL DEFAULT 'NORMAL' CHECK (urgencia IN ('NORMAL', 'ALTA', 'URGENTE')),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE' CHECK (status IN ('PENDENTE', 'ACEITO', 'RECUSADO', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 6. TABELA DE OFERTAS PUBLICADAS POR FREELANCERS
CREATE TABLE IF NOT EXISTS ofertas_servicos (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    freelancer_id VARCHAR(64) NOT NULL REFERENCES freelancers(id) ON DELETE CASCADE,
    category VARCHAR(50) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    price NUMERIC(10,2) NOT NULL,
    city VARCHAR(100) NOT NULL,
    availability VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 7. TABELA DE CARTEIRA E SALDO
CREATE TABLE IF NOT EXISTS carteira_saldo (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id VARCHAR(64) UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    disponivel NUMERIC(12,2) NOT NULL DEFAULT 0.00,
    pendente NUMERIC(12,2) NOT NULL DEFAULT 0.00,
    moeda VARCHAR(3) NOT NULL DEFAULT 'BRL',
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 8. TABELA DE TRANSAÇÕES FINANCEIRAS
CREATE TABLE IF NOT EXISTS transacoes_financeiras (
    id VARCHAR(64) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    usuario_id VARCHAR(64) NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    client_name VARCHAR(255) NOT NULL,
    service VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    data_transacao DATE NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDENTE' CHECK (status IN ('LIBERADO', 'PENDENTE', 'PROCESSANDO', 'CANCELADO')),
    withdrawable_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 9. TABELA DE CARTÕES DE PAGAMENTO
CREATE TABLE IF NOT EXISTS cartoes_pagamento (
    id VARCHAR(64) PRIMARY KEY DEFAULT gen_random_uuid()::text,
    usuario_id VARCHAR(64) NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    brand VARCHAR(50) NOT NULL,
    last4 VARCHAR(4) NOT NULL,
    holder_name VARCHAR(255) NOT NULL,
    expiry VARCHAR(10) NOT NULL,
    label VARCHAR(100) NOT NULL,
    is_primary BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 10. TABELA DE PLANOS DE ASSINATURA
CREATE TABLE IF NOT EXISTS planos (
    id VARCHAR(64) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    monthly_price NUMERIC(10,2) NOT NULL,
    benefits TEXT[] NOT NULL DEFAULT '{}',
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 11. TABELA DE ASSINATURAS DO USUÁRIO
CREATE TABLE IF NOT EXISTS assinaturas (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id VARCHAR(64) NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    plano_id VARCHAR(64) NOT NULL REFERENCES planos(id),
    active BOOLEAN NOT NULL DEFAULT FALSE,
    next_charge_date DATE,
    auto_renew BOOLEAN NOT NULL DEFAULT TRUE,
    auto_renew_method VARCHAR(50) NOT NULL DEFAULT 'PIX' CHECK (auto_renew_method IN ('PIX', 'CARD')),
    status VARCHAR(50) NOT NULL DEFAULT 'UP_TO_DATE' CHECK (status IN ('UP_TO_DATE', 'OVERDUE', 'CANCELLED')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 12. TABELA DO PROTOCOLO VIOLETA
CREATE TABLE IF NOT EXISTS protocolo_violeta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id VARCHAR(64) UNIQUE NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    enabled BOOLEAN NOT NULL DEFAULT FALSE,
    identity_verified BOOLEAN NOT NULL DEFAULT FALSE,
    phone_verified BOOLEAN NOT NULL DEFAULT FALSE,
    identity_verified_at TIMESTAMPTZ,
    phone_verified_at TIMESTAMPTZ,
    safe_route_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    emergency_button_enabled BOOLEAN NOT NULL DEFAULT FALSE,
    documento_frente_url TEXT,
    documento_verso_url TEXT,
    selfie_url TEXT,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 13. TABELA DE MÓDULOS DA TRILHA DE BLINDAGEM
CREATE TABLE IF NOT EXISTS trilha_modulos (
    id VARCHAR(64) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    ordem INT NOT NULL DEFAULT 1,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 14. TABELA DE LIÇÕES DA TRILHA
CREATE TABLE IF NOT EXISTS trilha_licoes (
    id VARCHAR(64) PRIMARY KEY,
    modulo_id VARCHAR(64) NOT NULL REFERENCES trilha_modulos(id) ON DELETE CASCADE,
    title VARCHAR(255) NOT NULL,
    ordem INT NOT NULL DEFAULT 1,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- 15. TABELA DE CARDS DE CONTEÚDO DAS LIÇÕES
CREATE TABLE IF NOT EXISTS trilha_cards (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    licao_id VARCHAR(64) NOT NULL REFERENCES trilha_licoes(id) ON DELETE CASCADE,
    heading VARCHAR(255) NOT NULL,
    body TEXT NOT NULL,
    tip TEXT,
    ordem INT NOT NULL DEFAULT 1,
    CONSTRAINT uq_trilha_cards_licao_ordem UNIQUE (licao_id, ordem)
);

-- 16. TABELA DE PROGRESSO DO USUÁRIO NA TRILHA
CREATE TABLE IF NOT EXISTS trilha_progresso_usuario (
    usuario_id VARCHAR(64) NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    licao_id VARCHAR(64) NOT NULL REFERENCES trilha_licoes(id) ON DELETE CASCADE,
    done BOOLEAN NOT NULL DEFAULT FALSE,
    completed_at TIMESTAMPTZ,
    PRIMARY KEY (usuario_id, licao_id)
);

-- =============================================================================
-- ÍNDICES PARA OTIMIZAÇÃO DE BUSCA E PERFORMANCE
-- =============================================================================
CREATE INDEX IF NOT EXISTS idx_usuarios_email ON usuarios(email);
CREATE INDEX IF NOT EXISTS idx_usuarios_firebase_uid ON usuarios(firebase_uid);
CREATE INDEX IF NOT EXISTS idx_enderecos_usuario ON enderecos(usuario_id);
CREATE INDEX IF NOT EXISTS idx_freelancers_category ON freelancers(category);
CREATE INDEX IF NOT EXISTS idx_freelancers_is_violeta ON freelancers(is_violeta);
CREATE INDEX IF NOT EXISTS idx_freelancers_is_pro ON freelancers(is_pro);
CREATE INDEX IF NOT EXISTS idx_freelancers_rating ON freelancers(rating DESC);
CREATE INDEX IF NOT EXISTS idx_propostas_cliente ON propostas_servicos(cliente_id);
CREATE INDEX IF NOT EXISTS idx_propostas_freelancer ON propostas_servicos(freelancer_id);
CREATE INDEX IF NOT EXISTS idx_avaliacoes_freelancer ON avaliacoes(freelancer_id);
CREATE INDEX IF NOT EXISTS idx_cartoes_usuario ON cartoes_pagamento(usuario_id);
CREATE INDEX IF NOT EXISTS idx_assinaturas_usuario ON assinaturas(usuario_id);
CREATE INDEX IF NOT EXISTS idx_ofertas_freelancer ON ofertas_servicos(freelancer_id);
CREATE INDEX IF NOT EXISTS idx_transacoes_usuario ON transacoes_financeiras(usuario_id);
CREATE INDEX IF NOT EXISTS idx_trilha_licoes_modulo ON trilha_licoes(modulo_id);
CREATE INDEX IF NOT EXISTS idx_trilha_cards_licao ON trilha_cards(licao_id);
