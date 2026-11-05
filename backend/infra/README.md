# Infraestrutura de Banco de Dados — Vértice App (Fase 1)

Guia de operação, manutenção e conexões da infraestrutura de banco de dados relacional provisionada para o aplicativo **Vértice**.

---

## 1. Visão Geral do Ambiente

- **Servidor:** Oracle Cloud VM (Ampere Altra ARM64, 24 GB RAM, 200 GB SSD)
- **Host / IP Público:** `137.131.242.237`
- **Engine:** PostgreSQL 16 (imagem multi-arch `postgres:16-alpine`)
- **Orquestração:** Docker Compose v2.30.3
- **Diretório de Produção na VM:** `/opt/vertice-db`
- **Volume Persistente de Dados:** `/opt/vertice-db/data`
- **Alocação de Memória:** 4 GB de limite máximo no container (`shared_buffers = 2GB`, `effective_cache_size = 8GB`)

---

## 2. Dados de Conexão (Fase 2 — Backend API)

| Parâmetro | Valor |
|---|---|
| **Host (Interno Docker)** | `postgres` ou `vertice_postgres` (porta `5432`) |
| **Host (Host Local da VM)** | `127.0.0.1` (porta `5432`) |
| **Database** | `vertice_db` |
| **Usuário** | `vertice_admin` |
| **Senha** | `Vertice_Db_2026_xP9#kL2!vQ` *(definida no `/opt/vertice-db/.env`)* |
| **Porta** | `5432` |

### Strings de Conexão

- **PostgreSQL URI Padrão:**
  ```text
  postgresql://vertice_admin:Vertice_Db_2026_xP9%23kL2!vQ@127.0.0.1:5432/vertice_db?sslmode=disable
  ```
- **JDBC (Spring Boot / Ktor / Kotlin):**
  ```text
  jdbc:postgresql://localhost:5432/vertice_db?user=vertice_admin&password=Vertice_Db_2026_xP9#kL2!vQ
  ```
- **Ambiente Docker (entre containers na rede `vertice-net`):**
  ```text
  DATABASE_URL=postgresql://vertice_admin:Vertice_Db_2026_xP9#kL2!vQ@postgres:5432/vertice_db
  ```

---

## 3. Conexão Visual Local via Túnel SSH (DBeaver / DataGrip / VS Code)

Para conectar ferramentas do seu computador de forma 100% segura sem expor a porta do banco abertamente na internet:

### Opção A: Abrir Túnel SSH via Terminal
Execute no seu terminal local (PowerShell ou Bash):
```powershell
ssh -i "C:\Users\user\Downloads\ssh-key-2026-04-08.key" -L 5432:localhost:5432 ubuntu@137.131.242.237 -N
```
Em seguida, configure o DBeaver/DataGrip conectando em:
- **Host:** `localhost`
- **Porta:** `5432`
- **Database:** `vertice_db`
- **Username:** `vertice_admin`
- **Password:** `Vertice_Db_2026_xP9#kL2!vQ`

### Opção B: Configurar SSH Tunnel direto no DBeaver
1. Abra as propriedades da conexão PostgreSQL no DBeaver.
2. Na aba **Main**: Host = `127.0.0.1`, Database = `vertice_db`, User = `vertice_admin`, Senha = `Vertice_Db_2026_xP9#kL2!vQ`.
3. Na aba **SSH**:
   - Marque **Use SSH Tunnel**.
   - Host/IP: `137.131.242.237`, Porta: `22`, Usuário: `ubuntu`.
   - Método de autenticação: **Public Key**.
   - Private key: selecione `C:\Users\user\Downloads\ssh-key-2026-04-08.key`.
4. Clique em **Test Connection**.

---

## 4. Comandos de Operação e Rotinas de Backup

Todos os comandos abaixo devem ser executados na VM em `/opt/vertice-db/`:

### Status do Banco e Logs
```bash
# Ver status e consumo de recursos
docker compose ps
docker stats vertice_postgres --no-stream

# Visualizar logs em tempo real
docker compose logs -f
```

### Execução de Consultas Rápidas via Terminal
```bash
docker exec -it vertice_postgres psql -U vertice_admin -d vertice_db
```

### Backup Manual Completo (Dump)
```bash
docker exec vertice_postgres pg_dump -U vertice_admin -d vertice_db -F c -b -v -f /var/lib/postgresql/data/backup_vertice_$(date +%Y%m%d_%H%M%S).dump
```

### Script de Automação de Backup Noturno (Cron)
Para agendar backup diário às 03:00 da manhã no host:
```bash
# Adicionar ao crontab (crontab -e):
0 3 * * * docker exec vertice_postgres pg_dump -U vertice_admin -d vertice_db | gzip > /opt/vertice-db/data/backup_$(date +\%Y\%m\%d).sql.gz
```

### Restauração de Backup
```bash
cat backup_vertice.sql | docker exec -i vertice_postgres psql -U vertice_admin -d vertice_db
```

---

## 5. Estrutura de Tabelas Criadas (16 Tabelas)

1. `usuarios` — Cadastro de contas, perfil, rating e badges.
2. `enderecos` — Endereços vinculados aos usuários com geolocalização.
3. `freelancers` — Catálogo de profissionais do Match, taxas, bio e tags.
4. `avaliacoes` — Avaliações e notas com comentários.
5. `propostas_servicos` — Solicitações de serviço entre clientes e prestadores.
6. `ofertas_servicos` — Anúncios publicados pelos freelancers.
7. `carteira_saldo` — Saldo disponível e pendente de cada usuário.
8. `transacoes_financeiras` — Histórico do extrato financeiro.
9. `cartoes_pagamento` — Cartões cadastrados (com tokenização/last4).
10. `planos` — Catálogo de planos de assinatura (ex: `plan_pro_monthly`).
11. `assinaturas` — Estado das assinaturas de cada usuário.
12. `protocolo_violeta` — Status da verificação e ferramentas de segurança.
13. `trilha_modulos` — Módulos da Trilha de Blindagem (m1 a m5).
14. `trilha_licoes` — Lições da capacitação (s1 a f3).
15. `trilha_cards` — Cartões educativos com orientações e dicas.
16. `trilha_progresso_usuario` — Registro de lições concluídas por usuário.
