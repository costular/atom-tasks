package com.costular.atomtasks.core.ui.utils

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun generateWindowSizeClass(): WindowSizeClass {
    val config = LocalConfiguration.current
    return WindowSizeClass.calculateFromSize(
        DpSize(
            config.screenWidthDp.dp,
            config.screenHeightDp.dp,
        ),
    )
}
