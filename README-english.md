[Read in Portuguese](README.md)

# Vértice — Android App (Compose)

**Android app (Kotlin + Jetpack Compose)** for **Vértice** — a platform that connects informal Brazilian entrepreneurs with strategic management partners. 1:1 port of the Figma Make prototype (`App.tsx`).

> **Empreenda Senac 2026 · 19th Edition** — functional MVP of the concept presented at Brazil's largest entrepreneurship and innovation competition.

---

## ✨ What the app does

Vértice tackles three real barriers of informal entrepreneurship:

1. **Offer & Request service** — the provider lists what they can do; the client searches and requests. Mutual reviews build verifiable reputation (the digital replacement for word-of-mouth).
2. **Protocolo Violeta** — an exclusive space for women entrepreneurs to connect with verified women only: verified identity, safe routes and 24h support. Safety as an economic lever, not a cost.
3. **Management partner matching** — the executor states the gap; the strategist states the skill; the platform proposes the match and formalizes the partnership (shared interest, not a loan).

The model charges **3–5%** only when the deal happens, with **escrow** releasing payment after the service is delivered.

---

## Screen-flows implemented (current stage)

- **Setup + Home** — greeting, profile progress card (70% ring), "Find Partner" / "Offer Service" buttons, recent activity, dismissable **Vértice Pro** banner.
- **Match** — search, area filters (Construction, Commerce, Services, Beauty, Food), professional cards with match %, rating (⭐/5), job count and availability. **Protocolo Violeta integration**: when active, shows only women providers.
- **Protocolo Violeta** — protection toggle with a "You are protected" overlay: 100% women-only network, verified identity, safe route, 24h support.
- **Profile + Shield Track** — "Ana Silva" profile (MIE), Protocolo Violeta badge, management/safety modules with persistent progress, Vértice Pro upgrade.
- **Modals**: Contact / Request Service (full form with type, date, budget, location, urgency), View Profile (recent reviews), Edit Profile, Offer Service and Vértice Pro (monthly plan R$ 19.99).

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3, Compose BOM 2024.06)
- **Navigation Compose** (state-driven tabs with `rememberSaveable`, rotation-safe)
- **Dark/Light** theme with the brand's purple/pink palette, top-right toggle
- Material extended icons

---

## Project Score

```
app/src/main/java/com/vertice/app/
├── components/     # reusable atoms (StatusBar, ProgressRing, Avatar, Pills...)
├── data/           # Freelancer + TrilhaData (seed)
├── nav/            # BottomNav + Screen enum
├── screens/        # Home, Match, Violeta, Perfil + 6 modals
└── ui/theme/       # Color, Theme (dark/light), Type
```
**~2,750 lines of Kotlin** · `minSdk 26` · `targetSdk 34` · `applicationId com.vertice.app`

---

## Step 1 — Open in Android Studio

1. Android Studio → **Open** → select the `VerticeApp` folder.
2. If asked to add the Gradle Wrapper (`gradlew`), accept — Android Studio downloads and configures it on its own.
3. Wait for the **Gradle Sync**. If it asks for SDK 34, install via SDK Manager.

## Step 2 — Run

- Create an emulator (Pixel 6/8, API 34) or connect a phone with USB debugging.
- `Run` (Shift+F10).

> Build (Windows): `gradle assembleDebug -Dorg.gradle.java.home="<Android Studio>\\jbr"` — host uses Java 21 (JBR), not the Java 25 default. APK at `app/build/outputs/apk/debug/app-debug.apk`.

---

## Step 3 — "Plus Jakarta Sans" font (pending)

The prototype uses `Plus Jakarta Sans` (weights 400–800). Steps:
1. Download from https://fonts.google.com/specimen/Plus+Jakarta+Sans ("Download family").
2. Add the static files to `app/src/main/res/font/` as `plus_jakarta_sans_regular.ttf`, `_medium.ttf`, `_semibold.ttf`, `_bold.ttf`, `_extrabold.ttf`.
3. Replace the contents of `ui/theme/Type.kt` with the corresponding `FontFamily`.
4. **Sync** again.

---

## Roadmap / Next steps

- [x] Setup + Home
- [x] Match + Contact modal
- [x] Protocolo Violeta + View Profile
- [x] Profile + Shield Track + Edit Profile
- [x] Offer Service modal + Vértice Pro modal
- [ ] Real backend (persistence + escrow) — currently a local `rememberSaveable` prototype
- [ ] Auth + identity verification (Protocolo Violeta)
- [ ] Store release (Play Console)

---

## Contribute / Test

Suggest, open an issue, or send a PR. The app is an MVP prototype — UX and Compose architecture feedback is very welcome.

**Vértice — Empreenda Senac 2026 · 19th Edition**