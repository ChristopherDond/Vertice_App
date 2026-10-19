package com.vertice.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class VerticeColors(
    val gradientStart: Color,
    val gradientEnd: Color,
    val success: Color,
    val warning: Color,
    val info: Color,
    val violeta: Color,
    val violetaSurface: Color,
    val gold: Color,
    val textSecondary: Color,
    val surfaceMuted: Color,
    val error: Color = Color(0xFFEF4444),
)

val LocalVerticeColors = staticCompositionLocalOf {
    VerticeColors(
        gradientStart = Purple500,
        gradientEnd = Pink500,
        success = SuccessGreen,
        warning = WarningAmber,
        info = InfoBlue,
        violeta = Violet400,
        violetaSurface = DarkSurfaceVariant,
        gold = Color(0xFFFBBF24),
        textSecondary = TextSecondaryDark,
        surfaceMuted = DarkSurfaceVariant,
        error = Color(0xFFEF4444),
    )
}

private val DarkScheme = darkColorScheme(
    primary = Purple400,
    onPrimary = Color(0xFF1B0630),
    primaryContainer = Purple700,
    onPrimaryContainer = Purple100,
    secondary = Pink400,
    onSecondary = Color(0xFF300A1E),
    secondaryContainer = Pink500,
    onSecondaryContainer = Color(0xFFFFE4F1),
    tertiary = Violet400,
    onTertiary = Color(0xFF1B0630),
    background = DarkBackground,
    onBackground = TextPrimaryDark,
    surface = DarkSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextSecondaryDark,
    outline = DarkOutline,
    outlineVariant = DarkOutline,
    error = ErrorRed,
    onError = Color(0xFF2A0505),
    scrim = Color(0xCC000000),
)

private val LightScheme = lightColorScheme(
    primary = Purple600,
    onPrimary = Color.White,
    primaryContainer = Purple100,
    onPrimaryContainer = Purple900,
    secondary = Pink500,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFE4F1),
    onSecondaryContainer = Color(0xFF4A0B29),
    tertiary = Violet400,
    onTertiary = Color.White,
    background = LightBackground,
    onBackground = TextPrimaryLight,
    surface = LightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = TextSecondaryLight,
    outline = LightOutline,
    outlineVariant = LightOutline,
    error = ErrorRed,
    onError = Color.White,
    scrim = Color(0x66000000),
)

@Composable
fun VerticeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val scheme = if (darkTheme) DarkScheme else LightScheme
    val extras = VerticeColors(
        gradientStart = if (darkTheme) Purple500 else Purple600,
        gradientEnd = if (darkTheme) Pink500 else Pink500,
        success = if (darkTheme) SuccessGreen else SuccessGreenDark,
        warning = WarningAmber,
        info = InfoBlue,
        violeta = if (darkTheme) Violet400 else Violet400,
        violetaSurface = if (darkTheme) Color(0xFF2A1A42) else Color(0xFFF0E9FF),
        gold = Color(0xFFFBBF24),
        textSecondary = if (darkTheme) TextSecondaryDark else TextSecondaryLight,
        surfaceMuted = if (darkTheme) DarkSurfaceVariant else LightSurfaceVariant,
    )
    CompositionLocalProvider(LocalVerticeColors provides extras) {
        MaterialTheme(
            colorScheme = scheme,
            typography = VerticeTypography,
            content = content,
        )
    }
}
