# Vertice App Improvements Implementation Plan

> **For Hermes:** Use hermes-dev-team skill to implement this plan in waves.

**Goal:** Implement validation/UX improvements in ContactModal (Contratar), fix filter button in MatchScreen, add 6 more freelancers with photos, and add smooth screen transitions.

**Architecture:** Kotlin/Compose Android app with 4 main screens (Home, Match, Violeta, Perfil) + modals. Changes span validation logic, UI components, data models, and navigation animations.

**Tech Stack:** Kotlin, Jetpack Compose, Material3, Navigation Compose (for transitions)

---

## Wave 1: Foundation (Parallel - 3 subagents)

### Task 1.1: Create ValidatedInput Components
**Objective:** Build reusable validated input components with masks, pickers, and validation

**Files:**
- Create: `app/src/main/java/com/vertice/app/components/ValidatedInput.kt`
- Modify: `app/src/main/java/com/vertice/app/components/FormAtoms.kt` (import new components)

**Step 1: Write failing test**
```kotlin
// Test date mask formatting
@Test fun testDateMaskFormatsCorrectly() {
    val result = DateMask.format("15082026")
    assertEquals("15/08/2026", result)
}
```

**Step 2: Run test to verify failure**
Run: `./gradlew test --tests "*.ValidatedInputTest"`
Expected: FAIL

**Step 3: Write minimal implementation**
Create `ValidatedInput.kt` with:
- `DateInput` - mask DD/MM/YYYY, calendar picker via `DatePickerDialog`
- `TimeInput` - mask HH:mm, time picker via `TimePickerDialog`
- `CurrencyInput` - numeric only, Brazilian format (R$ 0,00)
- `ValidatedTextField` - base component with validation state

**Step 4: Run test to verify pass**
Run: `./gradlew test --tests "*.ValidatedInputTest"`
Expected: PASS

**Step 5: Commit**
```bash
git add app/src/main/java/com/vertice/app/components/ValidatedInput.kt
git commit -m "feat: add validated input components with masks and pickers"
```

---

### Task 1.2: Extend Freelancer Data Model with Photos
**Objective:** Add photo support to Freelancer model and create 6 new freelancers with diverse profiles

**Files:**
- Modify: `app/src/main/java/com/vertice/app/data/Freelancer.kt` (add photo field)
- Modify: `app/src/main/java/com/vertice/app/components/Atoms.kt` (update Avatar to support images)

**Step 1: Write failing test**
```kotlin
@Test fun testFreelancerHasPhotoField() {
    val freelancer = Freelancer(..., photoRes = R.drawable.freelancer_1)
    assertNotNull(freelancer.photoRes)
}
```

**Step 2: Run test to verify failure**
Run: `./gradlew test --tests "*.FreelancerTest"`
Expected: FAIL

**Step 3: Write minimal implementation**
- Add `photoRes: Int?` or `photoUrl: String?` to Freelancer data class
- Update `Avatar` composable to accept optional `painter` parameter
- Add 6 new freelancers to `FREELANCERS` list with varied areas, genders, ratings
- Placeholder: use drawable resources or network images via Coil

**Step 4: Run test to verify pass**
Run: `./gradlew test --tests "*.FreelancerTest"`
Expected: PASS

**Step 5: Commit**
```bash
git add app/src/main/java/com/vertice/app/data/Freelancer.kt app/src/main/java/com/vertice/app/components/Atoms.kt
git commit -m "feat: add photo support to Freelancer and 6 new profiles"
```

---

### Task 1.3: Setup Navigation Animations Infrastructure
**Objective:** Configure Navigation Compose with shared element transitions and custom animations

**Files:**
- Create: `app/src/main/java/com/vertice/app/nav/AnimatedNavHost.kt`
- Modify: `app/src/main/java/com/vertice/app/MainActivity.kt` (integrate animated nav)
- Modify: `app/build.gradle.kts` (add navigation-compose dependency if missing)

**Step 1: Write failing test**
```kotlin
@Test fun testNavHostHasAnimations() {
    // Verify enter/exit animations configured
}
```

**Step 2: Run test to verify failure**
Expected: FAIL

**Step 3: Write minimal implementation**
- Add `navigation-compose` dependency
- Create `AnimatedNavHost` with `NavController` and custom `AnimatedNavOptions`
- Define enter/exit transitions: slide + fade, shared element for profile images
- Configure `NavDeepLinkBuilder` for deep links

**Step 4: Run test to verify pass**
Expected: PASS

**Step 5: Commit**
```bash
git add app/build.gradle.kts app/src/main/java/com/vertice/app/nav/AnimatedNavHost.kt app/src/main/java/com/vertice/app/MainActivity.kt
git commit -m "feat: setup navigation compose with custom animations"
```

---

## Wave 2: Core Implementation (Parallel - 3 subagents)

### Task 2.1: Refactor ContactModal with Validated Inputs
**Objective:** Replace all TInput fields in ContactModal with validated components

**Files:**
- Modify: `app/src/main/java/com/vertice/app/screens/ContactModal.kt`

**Step 1: Write failing test**
```kotlin
@Test fun testContactModalDateValidation() {
    // Date must be DD/MM/YYYY and future date
}
@Test fun testContactModalBudgetValidation() {
    // Budget must be numeric > 0
}
```

**Step 2: Run test to verify failure**
Expected: FAIL

**Step 3: Write minimal implementation**
- Replace date `TInput` → `DateInput` with calendar picker
- Replace time `TInput` → `TimeInput` with time picker
- Replace budget `TInput` → `CurrencyInput` with R$ formatting
- Add real-time validation feedback (red border, error text)
- Update `valid` condition to use new validators
- Keep all existing fields: service, date, hour, price, address, desc, photos, urgent

**Step 4: Run test to verify pass**
Expected: PASS

**Step 5: Commit**
```bash
git add app/src/main/java/com/vertice/app/screens/ContactModal.kt
git commit -m "feat: ContactModal with validated date/time/budget inputs and pickers"
```

---

### Task 2.2: Fix MatchScreen Filter Button
**Objective:** Make the filter button (FilterList icon) open a filter bottom sheet/dialog

**Files:**
- Modify: `app/src/main/java/com/vertice/app/screens/MatchScreen.kt`
- Create: `app/src/main/java/com/vertice/app/components/FilterBottomSheet.kt` (new)

**Step 1: Write failing test**
```kotlin
@Test fun testFilterButtonOpensBottomSheet() {
    // Click filter icon → bottom sheet visible
}
```

**Step 2: Run test to verify failure**
Expected: FAIL

**Step 3: Write minimal implementation**
- Add `ModalBottomSheetLayout` state to MatchScreen
- On filter icon click → `bottomSheetState.show()`
- Create `FilterBottomSheet` with:
  - Category chips (same as top chips but in sheet)
  - Violeta toggle
  - Search field
  - Clear filters button
  - Apply button
- Sync filter state between chips row and bottom sheet

**Step 4: Run test to verify pass**
Expected: PASS

**Step 5: Commit**
```bash
git add app/src/main/java/com/vertice/app/screens/MatchScreen.kt app/src/main/java/com/vertice/app/components/FilterBottomSheet.kt
git commit -m "feat: MatchScreen filter button opens bottom sheet with full filter options"
```

---

### Task 2.3: Implement Screen Transition Animations
**Objective:** Add smooth transitions between all screens (Home ↔ Match ↔ Violeta ↔ Perfil)

**Files:**
- Modify: `app/src/main/java/com/vertice/app/nav/AnimatedNavHost.kt`
- Modify: `app/src/main/java/com/vertice/app/nav/BottomNav.kt`
- Modify: `app/src/main/java/com/vertice/app/screens/*.kt` (add shared element keys)

**Step 1: Write failing test**
```kotlin
@Test fun testHomeToMatchTransition() {
    // Navigate Home→Match → verify slide+fade animation
}
```

**Step 2: Run test to verify failure**
Expected: FAIL

**Step 3: Write minimal implementation**
- Define `AnimatedNavOptions` for each route:
  - Home ↔ Match: horizontal slide (direction based on nav hierarchy)
  - Match ↔ Violeta: fade + scale
  - Home ↔ Perfil: shared element (avatar) + slide up
  - Modal transitions: slide up from bottom
- Add `Modifier.sharedElement()` to avatar/profile images
- Configure `NavController` animations in `AnimatedNavHost`
- Add haptic feedback on navigation

**Step 4: Run test to verify pass**
Expected: PASS

**Step 5: Commit**
```bash
git add app/src/main/java/com/vertice/app/nav/AnimatedNavHost.kt app/src/main/java/com/vertice/app/nav/BottomNav.kt app/src/main/java/com/vertice/app/screens/*.kt
git commit -m "feat: smooth screen transitions with shared elements"
```

---

## Wave 3: Quality & Polish (Parallel - 3 subagents)

### Task 3.1: Integration Testing & Edge Cases
**Objective:** Test all new functionality end-to-end, fix edge cases

**Files:**
- Modify: various (bug fixes)

**Tests to run:**
- ContactModal: date past validation, budget formatting, picker dismissal
- MatchScreen: filter persistence, Violeta + category combo, empty states
- Transitions: rapid nav, back press, modal + nav combo
- Freelancer photos: loading states, fallback to initials

**Verification:**
```bash
./gradlew connectedAndroidTest  # if device/emulator available
./gradlew test                  # unit tests
```

---

### Task 3.2: Performance & Accessibility Review
**Objective:** Ensure animations are 60fps, inputs accessible, no memory leaks

**Checks:**
- `Modifier.animate*()` not causing recomposition loops
- Image loading with Coil (placeholder, error, memory cache)
- Content descriptions on all interactive elements
- `prefersReducedMotion` respected (disable transitions if enabled)
- Large text scaling works

---

### Task 3.3: Final Polish & Build Verification
**Objective:** Clean build, lint, final visual verification

**Commands:**
```bash
./gradlew lintDebug
./gradlew assembleDebug
./gradlew bundleRelease  # verify release build
```

**Manual verification checklist:**
- [ ] ContactModal: date picker opens, formats DD/MM/YYYY, validates future date
- [ ] ContactModal: time picker opens, formats HH:mm
- [ ] ContactModal: budget formats R$ 1.234,56, numeric only
- [ ] MatchScreen: filter icon opens bottom sheet, filters work, clear works
- [ ] MatchScreen: 12 freelancers visible (6 original + 6 new), photos show
- [ ] Transitions: Home→Match slide, Match→Violeta fade, Home→Perfil shared avatar
- [ ] Modals: slide up/down smoothly
- [ ] Dark/light theme consistent
- [ ] Violeta protocol still works

---

## Risks & Tradeoffs

| Risk | Mitigation |
|------|------------|
| Navigation Compose migration breaks existing BottomNav | Keep BottomNav as-is, wrap in NavHost gradually |
| Coil dependency adds ~200KB | Use `Coil-compose` minimal; fallback to drawables if needed |
| Shared element transitions need Compose 1.6+ | Verify `compose-bom` version; use `AnimatedVisibility` fallback |
| Date/Time pickers native dialogs don't match theme | Accept system dialogs; custom pickers = scope creep |

---

## Dependencies to Add (build.gradle.kts)

```kotlin
// Navigation Compose
implementation("androidx.navigation:navigation-compose:2.8.3")

// Coil for image loading (if using network images)
implementation("io.coil-kt:coil-compose:2.6.0")

// Material3 Date/Time pickers (already in material3)
```

---

## File Change Summary

| File | Change Type |
|------|-------------|
| `FormAtoms.kt` | Import new ValidatedInput components |
| `ValidatedInput.kt` | **NEW** - DateInput, TimeInput, CurrencyInput, ValidatedTextField |
| `Freelancer.kt` | Add photoRes/photoUrl field |
| `Atoms.kt` | Avatar supports painter/image |
| `ContactModal.kt` | Replace TInput → validated inputs |
| `MatchScreen.kt` | Filter button → bottom sheet |
| `FilterBottomSheet.kt` | **NEW** - Full filter UI |
| `AnimatedNavHost.kt` | **NEW** - Navigation with animations |
| `BottomNav.kt` | Integrate with NavController |
| `MainActivity.kt` | Use AnimatedNavHost |
| `build.gradle.kts` | Add navigation-compose, coil-compose |

---

## Acceptance Criteria

1. **ContactModal (Contratar):**
   - Date field: tap → calendar picker, auto-mask DD/MM/YYYY, validates future date
   - Time field: tap → time picker, auto-mask HH:mm
   - Budget field: numeric only, formats as R$ 1.234,56
   - All fields show validation errors inline (red border + helper text)

2. **MatchScreen:**
   - Filter button (3 bars) opens bottom sheet with: category chips, Violeta toggle, search, clear/apply
   - Chips row and bottom sheet stay in sync
   - 12 freelancers displayed (6 original + 6 new) with profile photos

3. **App-wide Transitions:**
   - Screen changes: smooth slide/fade (300ms)
   - Profile avatar: shared element transition Home→Perfil
   - Modals: slide up from bottom
   - Back gesture/press: reverse animation

4. **Quality:**
   - `./gradlew assembleDebug` succeeds
   - `./gradlew lintDebug` no errors
   - All existing functionality preserved