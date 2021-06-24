package com.costular.atomhabits.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Teal200,
    primaryVariant = Teal200,
    secondary = Yellow200,
    onPrimary = OnTeal500,
    onSecondary = OnYellow500
)

private val LightColorPalette = lightColors(
    primary = Teal500,
    primaryVariant = Teal500,
    secondary = Yellow500,
    onPrimary = OnTeal500,
    onSecondary = OnYellow500,
    surface = LightSurface
)

@Composable
fun AtomHabitsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}