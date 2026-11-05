# Design Document: Infraestrutura de Banco de Dados Vértice (Fase 1)

- **Data:** 2026-11-05
- **Status:** Aprovado (decisões técnicas consolidadas)
- **Autor:** Antigravity & Equipe Vértice
- **Target:** Máquina Virtual Oracle Cloud (`137.131.242.237`, 24 GB RAM, 200 GB SSD, ARM64 / Ampere, Ubuntu 22.04 LTS)

---

## 1. Contexto e Motivação (Intent & Why)

O aplicativo **Vértice** é um app Android nativo (Kotlin + Jetpack Compose) focado em match de freelancers e prestadores de serviços, com proteção especial para mulheres e grupos vulneráveis através do **Protocolo Violeta**, além de capacitação profissional (**Trilha de Blindagem**) e gestão financeira.

Atualmente, o frontend consome dados fictícios estruturados em `FakeStore.kt` e contratos declarados em `Dtos.kt` e `Apis.kt`. Para transformar o aplicativo de um protótipo com mock frontend em um produto funcional com persistência real, a **Fase 1** estabelece a infraestrutura de banco de dados relacional (PostgreSQL 16) em nuvem, garantindo:
1. Schema relacional normalizado e consistente com as entidades do app.
2. Carga inicial completa (*seeds*) dos 12 freelancers, módulos da Trilha e planos de assinatura.
3. Alta performance aproveitando os 24 GB de RAM e armazenamento SSD da VM Oracle Cloud.
4. Isolamento e reprodutibilidade via Docker Compose.

---

## 2. Metas, Não-Metas e Anti-Metas

### Metas (Goals)
- Desativar o serviço PostgreSQL 14 nativo legado na VM (`sudo systemctl disable --now postgresql`) para liberar a porta padrão `5432`.
- Configurar o diretório de produção `/opt/vertice-db` na VM Oracle (`137.131.242.237`).
- Provisionar o container PostgreSQL 16 (imagem multi-arch oficial `postgres:16-alpine`) com volume persistente de dados em `/opt/vertice-db/data`.
- Ajustar os parâmetros de memória (`postgresql.conf`) especificamente para a capacidade de hardware da máquina de forma segura (`shared_buffers = 2GB`, `effective_cache_size = 8GB`, `random_page_cost = 1.1` para SSD, limite Docker de 4 GB RAM) para convivência harmoniosa com outros processos existentes no host.
- Criar o script DDL `01_schema.sql` cobrindo 100% dos agregados: `usuarios`, `enderecos`, `freelancers`, `avaliacoes`, `propostas_servicos`, `ofertas_servicos`, `carteira_saldo`, `transacoes_financeiras`, `cartoes_pagamento`, `planos`, `assinaturas`, `protocolo_violeta`, `trilha_modulos`, `trilha_licoes`, `trilha_cards` e `trilha_progresso_usuario`.
- Criar o script de seed `02_seeds.sql` populando o banco com os 12 freelancers reais do `FakeStore.kt`, a usuária demo (`ana.silva@vertice.app`), o plano Pro e os 5 módulos de blindagem com suas 15 lições e cards informativos.
- Validar a integridade dos dados e das constraints através de queries de verificação no container.
- Versionar uma cópia idêntica de todos os artefatos de infraestrutura no repositório local (`backend/infra/`).

### Não-Metas (Non-Goals)
- Desenvolver a camada de aplicação / API HTTP (Node.js/Ktor/Go/FastAPI) nesta fase. Essa será a Fase 2.
- Configurar certificados SSL de domínio público diretamente no Postgres (a comunicação externa será feita via túnel SSH seguro ou futuramente pela API local dentro da mesma rede Docker).

### Anti-Metas (Anti-Goals)
- Não expor o PostgreSQL na porta 5432 aberta para toda a internet (`0.0.0.0/0`) sem restrições ou senhas fracas.
- Não utilizar tipos genéricos de texto solto sem `CHECK constraints` ou chaves estrangeiras onde integridade referencial (`ON DELETE CASCADE` / `SET NULL`) é mandatória.
- Não causar indisponibilidade no host por consumo descontrolado de memória (OOM).

---

## 3. Restrições e Ambiente do Host (Constraints)

- **Arquitetura da CPU:** `aarch64` (ARM64 - Ampere Altra).
- **Sistema Operacional:** Ubuntu 22.04 LTS, usuário `ubuntu`, Docker 29.1.3 já presente.
- **Porta:** 5432 (liberada após desativação do postgresql-14 nativo).
- **Identificadores de Domínio:** O app usa identificadores textuais específicos para entidades de semente (ex: `"flw_camila"`, `"m1"`, `"s1"`, `"plan_pro_monthly"`). As PKs suportam `VARCHAR(64)`, enquanto novas entidades transacionais geram UUID v4.

---

## 4. Abordagem Selecionada (Approach)

**PostgreSQL 16 Otimizado via Docker Compose Declarativo:**
- Stack enxuta com `docker-compose.yml`, `.env` com credenciais seguras, volume persistente montado em `/opt/vertice-db/data`, scripts em `/opt/vertice-db/initdb/` (`01_schema.sql` e `02_seeds.sql`), e tuning em `postgresql.conf`.
- Inicialização determinística e rápida, sem overhead desnecessário, pronta para qualquer tecnologia de backend na Fase 2.

---

## 5. Arquitetura Detalhada e Decisões de Modelagem

### 5.1 Estrutura de Diretórios na VM (`/opt/vertice-db`) e Local (`backend/infra/`)
```text
/opt/vertice-db/ (e backend/infra/)
├── docker-compose.yml
├── .env
├── postgresql.conf
└── initdb/
    ├── 01_schema.sql
    └── 02_seeds.sql
```

### 5.2 Decisões de Engenharia Estabelecidas (Builder Calls)
1. **Conflito de Porta:** O serviço `postgresql` nativo será parado e desabilitado via `systemctl`, liberando a porta 5432 para o container Docker.
2. **Dimensionamento de Recursos:** Limite do container fixado em 4 GB de memória RAM (`deploy.resources.limits.memory: 4G`), prevenindo qualquer impacto nos outros serviços do host (servidor Java, etc.). Configurações do Postgres: `shared_buffers = 2GB`, `effective_cache_size = 8GB`, `work_mem = 16MB`, `maintenance_work_mem = 512MB`.
3. **Chave Estrangeira de Freelancers:** `freelancers.usuario_id` é `VARCHAR(64) NULL REFERENCES usuarios(id) ON DELETE SET NULL`. Apenas a freelancer `flw_ana` terá vínculo imediato com a usuária demo Ana Silva (`ana.silva@vertice.app`). Os outros 11 permanecem no catálogo vitrine sem a criação de contas fictícias desnecessárias.
4. **Máquinas de Estado:** Campos de status usam `VARCHAR(32)` com `CHECK constraint`:
   - `propostas_servicos.status`: `CHECK (status IN ('PENDENTE', 'ACEITO', 'RECUSADO', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO'))`
   - `propostas_servicos.urgencia`: `CHECK (urgencia IN ('NORMAL', 'ALTA', 'URGENTE'))`
   - `transacoes_financeiras.status`: `CHECK (status IN ('LIBERADO', 'PENDENTE', 'PROCESSANDO', 'CANCELADO'))`
   - `assinaturas.status`: `CHECK (status IN ('UP_TO_DATE', 'OVERDUE', 'CANCELLED'))`
   - `assinaturas.auto_renew_method`: `CHECK (auto_renew_method IN ('PIX', 'CARD'))`
5. **Métricas de Match:** `match_percent` e `distance_km` são mantidos como colunas com valores padrão para compatibilidade 1:1 imediata com o `FreelancerDto` do frontend.

---

## 6. Verificação e Validação

- Validação da subida com `docker compose ps` / `docker-compose ps` e `docker logs`.
- Queries SQL de verificação via `psql` validando:
  - Total de tabelas criadas no schema `public`.
  - Contagem de freelancers cadastrados (exatamente 12).
  - Contagem de módulos da Trilha (exatamente 5).
  - Contagem de lições (exatamente 15) e cards de capacitação.
  - Vínculo da usuária Ana Silva e sua assinatura do Plano Pro.
