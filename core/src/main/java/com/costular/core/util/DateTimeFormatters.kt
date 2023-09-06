package com.costular.core.util

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DateTimeFormatters {
    val dayOfWeekFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEE", Locale.getDefault())
    val shortDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("EEEEE", Locale.getDefault())
    val fullDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
}
