package com.costular.atomtasks.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import com.costular.core.util.DateTimeFormatters.formatTime
import java.time.LocalTime

@Composable
fun LocalTime.ofLocalizedTime(): String {
    val locale = LocalConfiguration.current.locale
    return remember(this, locale) {
        this.formatTime(locale)
    }
}
