[Versão em Português](README.md)

# Vértice — Android App (Compose)

1:1 port of the Figma Make prototype (`App.tsx`) to Kotlin + Jetpack Compose.

## Delivered in this stage
- Complete Gradle project (build.gradle.kts, AndroidManifest, theme).
- DARK/LIGHT color palette identical to the prototype (`ui/theme/Color.kt`), with dark/light toggle.
- `BottomNav` (footer) with the 4 items: Home, Match, Violeta, Profile.
- **Complete HomeScreen**: skippable Pro banner, greeting, profile progress card (70% ring), "Find Partner" / "Offer Service" buttons, recent activity.
- Match, Violeta, Profile: placeholders (coming in the next deliveries).

## Step 1 — Open in Android Studio
1. Extract the zip.
2. Android Studio → **Open** → select the `VerticeApp` folder.
3. If asked to add the Gradle Wrapper (`gradlew`), accept — Android Studio downloads and sets it up on its own.
4. Wait for the **Gradle Sync** (bottom bar). If it asks for SDK 34, install it via SDK Manager.

## Step 2 — Run
- Create an emulator (Pixel 8, API 34) or connect a phone with USB debugging enabled.
- Run ▶ (Shift+F10).

## Step 3 — "Plus Jakarta Sans" font (pending)
The prototype uses `Plus Jakarta Sans` (weights 400/500/600/700/800). To enable it:
1. Download it from https://fonts.google.com/specimen/Plus+Jakarta+Sans ("Download family" button).
2. From the downloaded zip, take the static files and place them in `app/src/main/res/font/` (create the folder) as:
   - `plus_jakarta_sans_regular.ttf`
   - `plus_jakarta_sans_medium.ttf`
   - `plus_jakarta_sans_semibold.ttf`
   - `plus_jakarta_sans_bold.ttf`
   - `plus_jakarta_sans_extrabold.ttf`
3. Open `ui/theme/Type.kt` and replace its contents with:
```kotlin
package com.vertice.app.ui.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.vertice.app.R

val PlusJakartaSans = FontFamily(
    Font(R.font.plus_jakarta_sans_regular, FontWeight.Normal),
    Font(R.font.plus_jakarta_sans_medium, FontWeight.Medium),
    Font(R.font.plus_jakarta_sans_semibold, FontWeight.SemiBold),
    Font(R.font.plus_jakarta_sans_bold, FontWeight.Bold),
    Font(R.font.plus_jakarta_sans_extrabold, FontWeight.ExtraBold),
)
```
4. Sync again.

## Next deliveries
1. ~~Setup + Home~~ ✅
2. ~~Match + Contact modal~~ ✅
3. ~~Violet Protocol + View Profile~~ ✅
4. ~~Profile + Shield Track + Edit Profile~~ ✅
5. Offer Service modal + Vértice Pro modal

Say "continue" and I'll move on to the final modals (Offer Service and Vértice Pro).
