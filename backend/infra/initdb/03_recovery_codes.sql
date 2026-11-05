-- =============================================================================
-- VÉRTICE APP — MIGRAÇÃO 03: TABELA DE CÓDIGOS DE RECUPERAÇÃO
-- Arquivo: 03_recovery_codes.sql
-- =============================================================================

CREATE TABLE IF NOT EXISTS codigos_recuperacao (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    contato VARCHAR(255) NOT NULL,
    codigo VARCHAR(10) NOT NULL,
    canal VARCHAR(20) NOT NULL DEFAULT 'EMAIL',
    expira_em TIMESTAMPTZ NOT NULL,
    usado BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX IF NOT EXISTS idx_codigos_recuperacao_contato ON codigos_recuperacao(contato, codigo);
