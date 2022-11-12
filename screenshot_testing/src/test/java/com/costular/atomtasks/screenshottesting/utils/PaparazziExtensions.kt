package com.costular.atomtasks.screenshottesting.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import app.cash.paparazzi.Paparazzi
import com.costular.designsystem.theme.AtomRemindersTheme

internal fun Paparazzi.screenshot(
    name: String? = null,
    isDarkTheme: Boolean = false,
    fontScale: Float = 1.0f,
    composable: @Composable () -> Unit,
) {
    snapshot(name) {
        CompositionLocalProvider(
            LocalDensity provides Density(
                density = LocalDensity.current.density,
                fontScale = fontScale,
            ),
        ) {
            AtomRemindersTheme(
                darkTheme = isDarkTheme,
            ) {
                Surface {
                    Box {
                        composable()
                    }
                }
            }
        }
    }
}
