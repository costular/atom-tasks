package com.costular.atomtasks.core.util

import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

object DateTimeFormatters {
    val shortDayOfWeekFormatter = DateTimeFormatter.ofPattern("E")
    val dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy")
    val dateWithMonthFormatter = DateTimeFormatter.ofPattern("d MMM")

    fun LocalTime.formatTime(locale: Locale): String =
        DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale).format(this)
}
