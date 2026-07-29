# Vértice — App Android (Compose)

Port 1:1 do protótipo Figma Make (`App.tsx`) pra Kotlin + Jetpack Compose.

## Entregue nesta etapa
- Projeto Gradle completo (build.gradle.kts, AndroidManifest, tema).
- Paleta de cores DARK/LIGHT idêntica ao protótipo (`ui/theme/Color.kt`), com toggle dark/light.
- `BottomNav` (footer) com os 4 itens: Início, Match, Violeta, Perfil.
- **HomeScreen completa**: banner Pro dispensável, saudação, card de progresso do perfil (anel 70%), botões "Encontrar Parceiro" / "Oferecer Serviço", atividade recente.
- Match, Violeta, Perfil: placeholders (chegam nas próximas entregas).

## Passo 1 — Abrir no Android Studio
1. Extraia o zip.
2. Android Studio → **Open** → selecione a pasta `VerticeApp`.
3. Se pedir para adicionar o Gradle Wrapper (`gradlew`), aceite — o Android Studio baixa e configura sozinho.
4. Aguarde o **Gradle Sync** (barra inferior). Se pedir SDK 34, instale via SDK Manager.

## Passo 2 — Rodar
- Crie um emulador (Pixel 8, API 34) ou conecte um celular com depuração USB.
- Run ▶ (Shift+F10).

## Passo 3 — Fonte "Plus Jakarta Sans" (pendente)
O protótipo usa `Plus Jakarta Sans` (pesos 400/500/600/700/800). Pra ativar:
1. Baixe em https://fonts.google.com/specimen/Plus+Jakarta+Sans (botão "Download family").
2. Do zip baixado, pegue os arquivos estáticos e renomeie/coloque em `app/src/main/res/font/` (criar a pasta) como:
   - `plus_jakarta_sans_regular.ttf`
   - `plus_jakarta_sans_medium.ttf`
   - `plus_jakarta_sans_semibold.ttf`
   - `plus_jakarta_sans_bold.ttf`
   - `plus_jakarta_sans_extrabold.ttf`
3. `ui/theme/Type.kt` já referencia esses nomes via `R.font.*` — assim que os arquivos existirem, o build resolve sozinho.
4. Sem os arquivos, o build **quebra** (R.font.* não existe). Se quiser rodar antes de baixar a fonte, me avisa que eu troco temporariamente pra `FontFamily.Default`.

## Próximas entregas (tela por tela)
1. ~~Setup + Home~~ ✅
2. Match (busca, filtros por chip, cards de freelancer, modal Contatar)
3. Protocolo Violeta (toggle + overlay "Você está protegida")
4. Perfil (habilidades, Trilha de Blindagem com lições, editar perfil)
5. Modais restantes: Oferecer Serviço, Vértice Pro, confirmação de envio

Manda "continuar" que sigo pro Match.
