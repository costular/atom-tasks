package com.costular.core.util

import java.time.format.DateTimeFormatter
import java.util.Locale

object DateTimeFormatters {
    val dayOfWeekFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEE", Locale.getDefault())
    val shortDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("EEEEE", Locale.getDefault())
    val fullDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("EEEE", Locale.getDefault())
    val dateFormatter =
        DateTimeFormatter.ofPattern("d MMM YY", Locale.getDefault())
    val timeFormatter =
        DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault())
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
}
