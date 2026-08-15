package com.vertice.app.nav

import android.content.Context
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween

/**
 * Main animated navigation host for Vertice app with custom screen transitions.
 * 
 * Transitions are configured via [getNavAnimationSpec] for each screen pair.
 * 
 * Transitions:
 * - Home ↔ Match: Slide horizontal
 * - Match ↔ Violeta: Fade + Scale
 * - Home ↔ Perfil: Shared element + Slide up
 * - Modal screens: Slide up/down (dialog destinations)
 */
fun slideHorizontal(): AnimationSpec<Float> = tween(
    durationMillis = 300,
    easing = { fraction -> fraction }
)

/**
 * Fade + Scale transition (Match ↔ Violeta)
 */
fun fadeScale(): AnimationSpec<Float> = tween(
    durationMillis = 300,
    easing = { fraction -> fraction }
)

/**
 * Shared element + Slide up transition (Home ↔ Perfil)
 */
fun sharedElementSlideUp(): AnimationSpec<Float> = tween(
    durationMillis = 400,
    easing = { fraction -> fraction }
)

/**
 * Modal slide up transition
 */
fun modalSlideUp(): AnimationSpec<Float> = tween(
    durationMillis = 300,
    easing = { fraction -> fraction }
)

/**
 * Modal slide down transition
 */
fun modalSlideDown(): AnimationSpec<Float> = tween(
    durationMillis = 250,
    easing = { fraction -> fraction }
)

/**
 * Default transition
 */
fun defaultTransition(): AnimationSpec<Float> = tween(
    durationMillis = 300,
    easing = { fraction -> fraction }
)

/**
 * Initialize navigation animations (no-op for now; provided for API compatibility).
 */
fun initNavigationAnimations(context: Context) {
    // Reserved for future animation initialization.
}