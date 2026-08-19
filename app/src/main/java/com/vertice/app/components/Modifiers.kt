package com.vertice.app.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isCtrlPressed
import androidx.compose.ui.input.pointer.isShiftPressed
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

private val WHEEL_LINE = 48f

private fun Modifier.dragWheel(state: ScrollState, axis: Axis): Modifier = composed {
    this.pointerInput(state, axis) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (event.type != PointerEventType.Scroll) continue
                val change = event.changes.first()
                val scrollDelta = if (axis == Axis.Vertical) {
                    if (event.keyboardModifiers.isShiftPressed) change.scrollDelta.x else change.scrollDelta.y
                } else {
                    if (event.keyboardModifiers.isCtrlPressed) change.scrollDelta.y else change.scrollDelta.x
                }
                if (scrollDelta == 0f) continue
                                state.dispatchRawDelta(scrollDelta * WHEEL_LINE)
                                change.consume()
            }
        }
    }
}

fun Modifier.verticalMouseScroll(state: ScrollState): Modifier = composed {
    this.verticalScroll(state).dragWheel(state, Axis.Vertical)
}

fun Modifier.horizontalMouseScroll(state: ScrollState): Modifier = composed {
    this.horizontalScroll(state).dragWheel(state, Axis.Horizontal)
}

enum class Axis { Vertical, Horizontal }

