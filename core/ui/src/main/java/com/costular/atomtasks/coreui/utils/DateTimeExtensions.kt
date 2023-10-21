package com.costular.atomtasks.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import com.costular.core.util.DateTimeFormatters.formatTime
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.time.temporal.TemporalAccessor
import java.util.Locale

@Composable
fun LocalTime.ofLocalizedTime(): String {
    val locale = LocalConfiguration.current.locale
    return remember(this, locale) {
        this.formatTime(locale)
    }
}

@Composable
fun YearMonth.ofLocalized(formatter: DateTimeFormatter): String {
    val locale = LocalConfiguration.current.locale
    return remember(this, locale) {
        this.format(formatter.withLocale(locale))
    }
}

@Composable
fun DayOfWeek.ofLocalized(style: TextStyle): String {
    val locale = LocalConfiguration.current.locale
    return remember(this, locale) {
        this.getDisplayName(style, locale)
    }
}

@Composable
fun DateTimeFormatter.ofLocalized(temporalAccessor: TemporalAccessor): String {
    val locale = LocalConfiguration.current.locale
    return remember(this, locale) {
        this.withLocale(locale).format(temporalAccessor)
    }
}
