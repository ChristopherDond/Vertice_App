package com.vertice.app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.clickableNoRipple(onClick: () -> Unit): Modifier = composed {
    val interaction = remember { MutableInteractionSource() }
    this.clickable(indication = null, interactionSource = interaction, onClick = onClick)
}

fun Modifier.clickableRipple(onClick: () -> Unit): Modifier = composed {
    this.clickable(onClick = onClick)
}

fun Modifier.drawTopBorder(color: Color, strokeWidth: Dp = 1.dp): Modifier = drawBehind {
    drawLine(
        color = color,
        start = Offset(0f, 0f),
        end = Offset(size.width, 0f),
        strokeWidth = strokeWidth.toPx(),
    )
}

fun Modifier.drawBottomBorder(color: Color, strokeWidth: Dp = 1.dp): Modifier = drawBehind {
    drawLine(
        color = color,
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = strokeWidth.toPx(),
    )
}

