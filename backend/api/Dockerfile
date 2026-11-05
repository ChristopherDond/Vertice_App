# =============================================================================
# VÉRTICE API — MULTI-STAGE DOCKERFILE (ARM64 / LINUX)
# =============================================================================

# Estágio 1: Build
FROM node:22-alpine AS builder

WORKDIR /app

COPY package*.json tsconfig.json ./
RUN npm ci

COPY src ./src
RUN npm run build && npm prune --production

# Estágio 2: Runner de Produção
FROM node:22-alpine AS runner

WORKDIR /app
ENV NODE_ENV=production

# Usuário não-root para segurança
RUN addgroup -S vertice && adduser -S vertice -G vertice

COPY package*.json ./
COPY --from=builder /app/node_modules ./node_modules
COPY --from=builder /app/dist ./dist

USER vertice
EXPOSE 3000

CMD ["node", "dist/server.js"]
