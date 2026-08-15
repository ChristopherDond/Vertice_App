package com.vertice.app.nav

import androidx.compose.animation.sharedelement.SharedTransitionApi
import androidx.compose.runtime.Stable

/**
 * Shared element keys for the Vertice app.
 * These keys must match between source and destination composables.
 */
object SharedElementKeys {
    const val PROFILE_AVATAR = "profile_avatar"
    const val FREELANCER_AVATAR = "freelancer_avatar"
    const val MATCH_CARD = "match_card"
    const val PROFILE_CARD = "profile_card"
    const val HERO_CARD = "hero_card"
}

/**
 * Manages shared element transitions across navigation.
 * Provides a way to track and trigger shared element animations.
 */
@Stable
class SharedElementManager {
    private var currentTransitionKey: String? = null
    private var isNavigating = false

    fun startTransition(key: String) {
        currentTransitionKey = key
        isNavigating = true
    }

    fun endTransition() {
        currentTransitionKey = null
        isNavigating = false
    }

    val isTransitioning: Boolean
        get() = isNavigating

    val transitionKey: String?
        get() = currentTransitionKey
}

/**
 * Extension functions for shared element transitions
 */
fun SharedTransitionApi.addProfileAvatarSharedElement(
    modifier: androidx.compose.ui.Modifier,
    key: String = SharedElementKeys.PROFILE_AVATAR
): androidx.compose.ui.Modifier {
    return modifier
        .sharedElement(key) { rect, _ ->
            // Customize the shared element appearance during transition
        }
}

/**
 * Haptic feedback helper
 */
object HapticFeedback {
    private var hapticEngine: androidx.haptics.HapticFeedbackController? = null

    fun initialize(context: android.content.Context) {
        hapticEngine = androidx.haptics.HapticFeedbackController(context)
    }

    fun lightClick() {
        hapticEngine?.performHapticFeedback(
            android.view.View.HAPTIC_FEEDBACK_KEYBOARD_TAP,
            android.os.VibrationEffect.DEFAULT_AMPLITUDE
        )
    }

    fun mediumClick() {
        hapticEngine?.performHapticFeedback(
            android.view.View.HAPTIC_FEEDBACK_LONG_PRESS,
            android.os.VibrationEffect.DEFAULT_AMPLITUDE
        )
    }

    fun heavyClick() {
        hapticEngine?.performHapticFeedback(
            android.view.View.HAPTIC_FEEDBACK_VIRTUAL_KEY,
            android.os.VibrationEffect.DEFAULT_AMPLITUDE
        )
    }

    fun success() {
        hapticEngine?.performHapticFeedback(
            android.view.View.HAPTIC_FEEDBACK_KEYBOARD_TAP,
            android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_CLICK)
        )
    }

    fun warning() {
        hapticEngine?.performHapticFeedback(
            android.view.View.HAPTIC_FEEDBACK_LONG_PRESS,
            android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_DOUBLE_CLICK)
        )
    }

    fun error() {
        hapticEngine?.performHapticFeedback(
            android.view.View.HAPTIC_FEEDBACK_KEYBOARD_TAP,
            android.os.VibrationEffect.createPredefined(android.os.VibrationEffect.EFFECT_TICK)
        )
    }
}