package com.costular.atomtasks.ui.util

import java.time.format.DateTimeFormatter
import java.util.*

object DateTimeFormatters {
    val dayOfWeekFormatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern("EEE", Locale.getDefault()) // TODO: 18/6/21 fix this
    val shortDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("EEEEE", Locale.getDefault()) // TODO: 18/6/21 fix this
    val fullDayOfWeekFormatter =
        DateTimeFormatter.ofPattern("EEEE", Locale.getDefault()) // TODO: 18/6/21 fix this
    val dateFormatter =
        DateTimeFormatter.ofPattern("d MMM", Locale.getDefault()) // TODO: 18/6/21 fix this
    val timeFormatter =
        DateTimeFormatter.ofPattern("HH:mm", Locale.getDefault()) // TODO: 25/6/21 Fix this
}
