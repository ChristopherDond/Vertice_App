package com.vertice.app.ui.theme

import androidx.compose.ui.graphics.Color

data class VerticeColors(
    val navy: Color,
    val card: Color,
    val card2: Color,
    val purple: Color,
    val purpleL: Color,
    val pink: Color,
    val white: Color,
    val muted: Color,
    val mutedL: Color,
    val green: Color,
    val amber: Color,
    val border: Color,
    val glass: Color,
    val inputBg: Color,
)

val DarkColors = VerticeColors(
    navy = Color(0xFF0D0F1C),
    card = Color(0xFF1A1D30),
    card2 = Color(0xFF222540),
    purple = Color(0xFF7C3AED),
    purpleL = Color(0xFF9B5FF7),
    pink = Color(0xFFEC4899),
    white = Color(0xFFFFFFFF),
    muted = Color(0xFF7B82A8),
    mutedL = Color(0xFFA0A8CC),
    green = Color(0xFF10B981),
    amber = Color(0xFFF59E0B),
    border = Color(0x12FFFFFF),
    glass = Color(0x0AFFFFFF),
    inputBg = Color(0xFF222540),
)

val LightColors = VerticeColors(
    navy = Color(0xFFF0EDFF),
    card = Color(0xFFFFFFFF),
    card2 = Color(0xFFF4F2FF),
    purple = Color(0xFF7C3AED),
    purpleL = Color(0xFF7C3AED),
    pink = Color(0xFFEC4899),
    white = Color(0xFF1A1235),
    muted = Color(0xFF7B78A8),
    mutedL = Color(0xFF4E4A75),
    green = Color(0xFF059669),
    amber = Color(0xFFD97706),
    border = Color(0x14000000),
    glass = Color(0x08000000),
    inputBg = Color(0xFFEDE9FF),
)

fun Color.withAlpha(alpha: Int): Color = this.copy(alpha = alpha / 255f)

