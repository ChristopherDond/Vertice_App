package com.vertice.app.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color

val PressWhite = Color(0x38FFFFFF)
val PressAccent = Color(0x33904EF2)
val PressSoft = Color(0x1F4D3274)

fun Modifier.vClick(
    tint: Color = PressAccent,
    scaleDown: Float = 0.97f,
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && enabled) scaleDown else 1f,
        animationSpec = tween(110),
        label = "vPress",
    )
    this
        .scale(scale)
        .clickable(
            interactionSource = source,
            indication = rememberRipple(bounded = true, color = tint),
            enabled = enabled,
            onClick = onClick,
        )
}

fun Modifier.vPress(
    scaleDown: Float = 0.97f,
    enabled: Boolean = true,
    onClick: () -> Unit,
): Modifier = composed {
    val source = remember { MutableInteractionSource() }
    val pressed by source.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (pressed && enabled) scaleDown else 1f,
        animationSpec = tween(110),
        label = "vPress",
    )
    this
        .scale(scale)
        .clickable(
            interactionSource = source,
            indication = null,
            enabled = enabled,
            onClick = onClick,
        )
}
