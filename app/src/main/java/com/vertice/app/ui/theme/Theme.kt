package com.vertice.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle

/** Equivalente ao ThemeCtx (createContext) do React: expõe cores + toggle dark/light. */
data class VerticeTheme(
    val colors: VerticeColors,
    val dark: Boolean,
    val toggle: () -> Unit,
)

val LocalVerticeTheme = staticCompositionLocalOf {
    VerticeTheme(colors = DarkColors, dark = true, toggle = {})
}

/** Atalho equivalente ao `useC()` do React. */
val LocalColors
    @Composable get() = LocalVerticeTheme.current.colors

private val baseStyle = TextStyle(fontFamily = PlusJakartaSans)
private val AppTypography = Typography(
    displayLarge = baseStyle, displayMedium = baseStyle, displaySmall = baseStyle,
    headlineLarge = baseStyle, headlineMedium = baseStyle, headlineSmall = baseStyle,
    titleLarge = baseStyle, titleMedium = baseStyle, titleSmall = baseStyle,
    bodyLarge = baseStyle, bodyMedium = baseStyle, bodySmall = baseStyle,
    labelLarge = baseStyle, labelMedium = baseStyle, labelSmall = baseStyle,
)

@Composable
fun VerticeThemeProvider(
    dark: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit,
) {
    val theme = VerticeTheme(
        colors = if (dark) DarkColors else LightColors,
        dark = dark,
        toggle = onToggle,
    )
    CompositionLocalProvider(LocalVerticeTheme provides theme) {
        MaterialTheme(typography = AppTypography, content = content)
    }
}
