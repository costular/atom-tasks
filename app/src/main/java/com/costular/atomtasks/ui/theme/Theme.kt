package com.costular.atomtasks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

private val DarkColorPalette = darkColors(
    primary = Teal200,
    primaryVariant = Teal200,
    secondary = Yellow200,
    onPrimary = OnTeal500,
    onSecondary = OnYellow500,
    surface = DarkSurface,
)

private val LightColorPalette = lightColors(
    primary = Teal500,
    primaryVariant = Teal500,
    secondary = Yellow500,
    onPrimary = OnTeal500,
    onSecondary = OnYellow500,
    surface = LightSurface,
)

@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit,
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}

private val LocalAppDimens = staticCompositionLocalOf {
    CompactDimensions
}

@Composable
fun AtomRemindersTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    val configuration = LocalConfiguration.current
    val dimensions = when {
        configuration.screenWidthDp < SmallWidth -> CompactDimensions
        configuration.screenWidthDp < MediumWidth -> MediumDimensions
        else -> ExpandedDimensions
    }

    ProvideDimens(dimensions = dimensions) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}

object AppTheme {
    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current

    val ChipIconSize = 18.dp
}

const val SmallWidth = 600
const val MediumWidth = 840
