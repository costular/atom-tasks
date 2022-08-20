package com.costular.atomtasks.screenshottesting.utils

internal enum class Theme { LIGHT, DARK }
internal enum class FontSize { NORMAL, BIG }

internal fun Theme.isDarkTheme(): Boolean = this == Theme.DARK

internal fun FontSize.asFloat(): Float = when (this) {
    FontSize.NORMAL -> 1.0f
    FontSize.BIG -> 1.5f
}
