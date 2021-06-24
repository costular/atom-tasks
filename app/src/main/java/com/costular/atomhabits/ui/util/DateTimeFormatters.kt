package com.costular.atomhabits.ui.util

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

object DateTimeFormatters {
    val dayOfWeekFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEE", Locale.getDefault()) // TODO: 18/6/21 fix this
    val shortDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("E", Locale.getDefault())  // TODO: 18/6/21 fix this
    val dateFormatter =
        DateTimeFormatter.ofPattern("d MMM", Locale.getDefault())  // TODO: 18/6/21 fix this
}