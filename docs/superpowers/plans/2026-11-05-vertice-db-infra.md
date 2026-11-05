# Infraestrutura de Banco de Dados Vértice (Fase 1) — Plano de Implementação

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Provisionar o banco relacional PostgreSQL 16 via Docker Compose na VM Oracle Cloud (`137.131.242.237`), com tuning de hardware para 24 GB de RAM, schema relacional normalizado para todos os DTOs do app Vértice e 100% dos dados de seed extraídos do `FakeStore.kt`.

**Architecture:** Estrutura declarativa e desacoplada em `/opt/vertice-db` (e espelhada localmente em `backend/infra/`) com container oficial `postgres:16-alpine`, volume persistente, tuning em `postgresql.conf`, inicialização automática via `/docker-entrypoint-initdb.d` (`01_schema.sql` e `02_seeds.sql`), e validação automatizada de integridade referencial.

**Tech Stack:** PostgreSQL 16, Docker Engine 29, Docker Compose v2, Bash/SSH, SQL (PostgreSQL DDL/DML).

**Spec:** `docs/superpowers/specs/2026-11-05-vertice-db-infra-design.md`

## Global Constraints

- Arquitetura alvo da VM é `aarch64` (ARM64 Ampere Altra).
- Host: Ubuntu 22.04 LTS, usuário `ubuntu`, chave privada `C:\Users\user\Downloads\ssh-key-2026-04-08.key`.
- Limite de memória do container Docker fixado em 4 GB para coexistir com outros processos do servidor.
- Serviço nativo legado `postgresql` deve ser parado e desabilitado antes de subir o container na porta 5432.
- Os identificadores de semente devem coincidir exatamente com os do app (`flw_camila`, `flw_ana`, `m1`, `s1`, `plan_pro_monthly`).
- O schema e os seeds devem ficar versionados também no repositório local em `backend/infra/`.

## Review Focus

1. **Port Bind Conflict:** O serviço nativo PostgreSQL 14 precisa estar 100% desativado antes de subir o container para evitar erro de `address already in use` na porta 5432.
2. **ARM64 Image Compatibility:** A imagem Docker `postgres:16-alpine` deve rodar nativamente em Linux aarch64 sem emulação.
3. **Foreign Key Integrity:** `freelancers.usuario_id` deve ser nullable; apenas Ana Silva é vinculada ao seed de usuário, evitando erro de integridade referencial ou usuários falsos.
4. **Seed Fidelity:** Todos os 12 freelancers, 5 módulos e 15 lições de `FakeStore.kt` com seus cards devem estar presentes no banco com todos os campos preenchidos.
5. **Memory Cap Safety:** Limite de memória explícito de 4 GB no container para proteção contra OOM no host compartilhado.

---

### Task 1: Estruturação dos Artefatos de Infraestrutura Locais (`backend/infra/`)

**Files:**
- Create: `backend/infra/docker-compose.yml`
- Create: `backend/infra/.env.example`
- Create: `backend/infra/postgresql.conf`
- Create: `backend/infra/initdb/01_schema.sql`
- Create: `backend/infra/initdb/02_seeds.sql`
- Create: `backend/infra/verify_db.sql`

- [x] Step 1: Criar o arquivo `backend/infra/initdb/01_schema.sql` contendo todas as 16 tabelas relacionais com PKs, FKs (`ON DELETE CASCADE` / `ON DELETE SET NULL`), tipos com timezone (`TIMESTAMPTZ`), arrays (`TEXT[]`), constraints `CHECK` para status e índices de alta performance (`idx_usuarios_email`, `idx_freelancers_category`, `idx_propostas_cliente`, etc.).
- [x] Step 2: Criar o arquivo `backend/infra/initdb/02_seeds.sql` contendo os dados de seed extraídos com exatidão de `FakeStore.kt`:
  - Usuária demo Ana Silva (`ana.silva@vertice.app`, hash bcrypt, carteira zerada, status violeta inicial).
  - 12 freelancers completos (`flw_camila`, `flw_carlos`, `flw_andre`, `flw_luisa`, `flw_beatriz`, `flw_regina`, `flw_joao`, `flw_marina`, `flw_ana`, `flw_roberto`, `flw_diego`, `flw_juliana`).
  - Plano Pro mensal (`plan_pro_monthly`, R$ 19,99) com seus 5 benefícios oficiais.
  - 5 módulos da Trilha de Blindagem (`m1` a `m5`) com suas 15 lições (`s1`-`s3`, `d1`-`d3`, `c1`-`c3`, `g1`-`g3`, `f1`-`f3`) e todos os cards informativos (`heading`, `body`, `tip`).
- [x] Step 3: Criar o arquivo `backend/infra/postgresql.conf` com tuning seguro: `shared_buffers = 2GB`, `effective_cache_size = 8GB`, `work_mem = 16MB`, `maintenance_work_mem = 512MB`, `checkpoint_completion_target = 0.9`, `random_page_cost = 1.1`, `effective_io_concurrency = 200`, `max_connections = 100`.
- [x] Step 4: Criar `backend/infra/docker-compose.yml` definindo o serviço `postgres` (`image: postgres:16-alpine`), portas `127.0.0.1:5432:5432`, volumes para `./data` e `./initdb`, arquivo de configuração montado, healthcheck e limite de memória de 4 GB (`deploy.resources.limits.memory: 4G`).
- [x] Step 5: Criar `backend/infra/.env.example` e `backend/infra/verify_db.sql` com queries de validação de contagem e consistência de dados.
- [x] Step 6: Validar a sintaxe dos arquivos SQL gerados.

---

### Task 2: Preparação do Ambiente Remoto na VM Oracle via SSH

**Files:**
- Modify: Configurações do sistema operacional da VM (`137.131.242.237`)

- [x] Step 1: Conectar via SSH e verificar o status atual do serviço `postgresql` nativo (`sudo systemctl status postgresql`).
- [x] Step 2: Parar e desabilitar permanentemente o serviço `postgresql` nativo (`sudo systemctl disable --now postgresql`).
- [x] Step 3: Confirmar que a porta 5432 está liberada no host (`sudo ss -tulpn | grep 5432` deve retornar vazio).
- [x] Step 4: Instalar o pacote `docker-compose-plugin` / binary v2.30.3 no Ubuntu para ter suporte ao comando nativo `docker compose`.
- [x] Step 5: Adicionar o usuário `ubuntu` ao grupo `docker` para permitir execução de comandos sem necessidade de sudo (`sudo usermod -aG docker ubuntu`).
- [x] Step 6: Criar a pasta de deploy `/opt/vertice-db` e seus subdiretórios (`initdb`, `data`) definindo permissões para o usuário `ubuntu` (`sudo mkdir -p /opt/vertice-db/initdb /opt/vertice-db/data && sudo chown -R ubuntu:ubuntu /opt/vertice-db`).

---

### Task 3: Deploy dos Arquivos de Configuração na VM

**Files:**
- Create: `/opt/vertice-db/docker-compose.yml`
- Create: `/opt/vertice-db/.env`
- Create: `/opt/vertice-db/postgresql.conf`
- Create: `/opt/vertice-db/initdb/01_schema.sql`
- Create: `/opt/vertice-db/initdb/02_seeds.sql`

- [x] Step 1: Gerar uma senha forte aleatória para o banco de dados e criar o arquivo `.env` seguro na VM com `POSTGRES_USER=vertice_admin`, `POSTGRES_PASSWORD=<SENHA_FORTE>`, `POSTGRES_DB=vertice_db`, `POSTGRES_PORT=5432`.
- [x] Step 2: Transferir os arquivos locais (`docker-compose.yml`, `postgresql.conf`, `initdb/01_schema.sql`, `initdb/02_seeds.sql`, `verify_db.sql`) para a VM em `/opt/vertice-db/` via SSH/SCP.
- [x] Step 3: Verificar a integridade dos arquivos transferidos no host remoto via `ls -la /opt/vertice-db/initdb/` e checksums.

---

### Task 4: Inicialização dos Containers e Execução da Inicialização do Banco

**Files:**
- Target: Container `vertice_postgres` na VM

- [x] Step 1: Executar `docker compose up -d` no diretório `/opt/vertice-db`.
- [x] Step 2: Monitorar os logs de inicialização via `docker compose logs -f` acompanhando a execução ordenada de `01_schema.sql` e `02_seeds.sql`.
- [x] Step 3: Aguardar o log registrar `"PostgreSQL init process complete; ready for start up"` e `"database system is ready to accept connections"`.
- [x] Step 4: Verificar o status do healthcheck do container (`docker compose ps` deve mostrar status `healthy` / `Up`).

---

### Task 5: Validação Automatizada de Dados e Integridade Relacional

**Files:**
- Target: Banco `vertice_db` dentro do container

- [x] Step 1: Executar query de contagem de tabelas criadas no schema `public` (retornou 16 tabelas).
- [x] Step 2: Executar query verificando os 12 freelancers inseridos (nome, categoria, rating, match_percent, tags).
- [x] Step 3: Executar query verificando a usuária demo Ana Silva e seu registro de saldo em `carteira_saldo`.
- [x] Step 4: Executar query verificando a existência do Plano Pro em `planos`.
- [x] Step 5: Executar query verificando a contagem de módulos (5), lições (15) e cards de capacitação (34).
- [x] Step 6: Testar integridade referencial: simular inserção de um pedido de serviço e validar restrição de chave estrangeira.

---

### Task 6: Documentação Operacional, Scripts de Manutenção e Handoff

**Files:**
- Create: `backend/infra/README.md`

- [x] Step 1: Documentar os procedimentos de backup e restore com `pg_dump` e `pg_restore`.
- [x] Step 2: Documentar como conectar via túnel SSH no DBeaver / DataGrip / VS Code (comando `ssh -L 5432:localhost:5432 ...`).
- [x] Step 3: Documentar as strings de conexão JDBC, Postgres URI e variáveis de ambiente necessárias para a Fase 2 (desenvolvimento da API).
- [x] Step 4: Apresentar o relatório final consolidado ao usuário.
