package com.costular.designsystem.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class Dimensions(
    val contentMargin: Dp,
    val spacingSmall: Dp,
    val spacingMedium: Dp,
    val spacingLarge: Dp,
    val spacingXLarge: Dp,
    val spacingHuge: Dp,
)

val CompactDimensions = Dimensions(
    contentMargin = 16.dp,
    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingLarge = 16.dp,
    spacingXLarge = 24.dp,
    spacingHuge = 32.dp,
)

val MediumDimensions = Dimensions(
    contentMargin = 32.dp,
    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingLarge = 16.dp,
    spacingXLarge = 24.dp,
    spacingHuge = 32.dp,
)

val ExpandedDimensions = Dimensions(
    contentMargin = 200.dp,
    spacingSmall = 4.dp,
    spacingMedium = 8.dp,
    spacingLarge = 16.dp,
    spacingXLarge = 24.dp,
    spacingHuge = 32.dp,
)
