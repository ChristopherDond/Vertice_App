-- =============================================================================
-- VÉRTICE APP — SCRIPT DE VALIDAÇÃO E AUDITORIA DO BANCO DE DADOS
-- Arquivo: verify_db.sql
-- =============================================================================

\echo '--- 1. CONTAGEM TOTAL DE TABELAS CRIADAS NO SCHEMA PUBLIC ---'
SELECT count(*) AS total_tabelas
FROM information_schema.tables
WHERE table_schema = 'public' AND table_type = 'BASE TABLE';

\echo '--- 2. LISTAGEM DAS TABELAS REGISTRADAS ---'
SELECT table_name
FROM information_schema.tables
WHERE table_schema = 'public' AND table_type = 'BASE TABLE'
ORDER BY table_name;

\echo '--- 3. VERIFICAÇÃO DA USUÁRIA DEMO (ANA SILVA) E SALDO ---'
SELECT u.id, u.name, u.email, u.cpf, u.is_pro, u.violeta_verified, s.disponivel, s.moeda
FROM usuarios u
LEFT JOIN carteira_saldo s ON s.usuario_id = u.id
WHERE u.id = 'usr_ana';

\echo '--- 4. CONTAGEM E RESUMO DE FREELANCERS ---'
SELECT count(*) AS total_freelancers FROM freelancers;

SELECT category, count(*) AS qtd, round(avg(hourly_rate), 2) AS taxa_media
FROM freelancers
GROUP BY category
ORDER BY qtd DESC;

\echo '--- 5. VERIFICAÇÃO DO VÍNCULO VIOLETA E PLANO PRO NOS FREELANCERS ---'
SELECT id, name, role, category, rating, is_violeta, is_pro, hourly_rate
FROM freelancers
ORDER BY rating DESC, name ASC;

\echo '--- 6. VERIFICAÇÃO DO PLANO DE ASSINATURA ---'
SELECT id, name, monthly_price, array_length(benefits, 1) AS total_beneficios
FROM planos;

\echo '--- 7. VERIFICAÇÃO DA TRILHA DE BLINDAGEM (MÓDULOS, LIÇÕES E CARDS) ---'
SELECT m.id AS modulo_id, m.title AS modulo_titulo, count(DISTINCT l.id) AS qtd_licoes, count(c.id) AS qtd_cards
FROM trilha_modulos m
LEFT JOIN trilha_licoes l ON l.modulo_id = m.id
LEFT JOIN trilha_cards c ON c.licao_id = l.id
GROUP BY m.id, m.title, m.ordem
ORDER BY m.ordem;

\echo '--- 8. TOTAIS GERAIS DA TRILHA ---'
SELECT 
    (SELECT count(*) FROM trilha_modulos) AS total_modulos,
    (SELECT count(*) FROM trilha_licoes) AS total_licoes,
    (SELECT count(*) FROM trilha_cards) AS total_cards;
