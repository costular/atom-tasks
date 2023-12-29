package com.costular.atomtasks.core.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale

object WeekUtil {
    fun getFirstDayOfWeek(locale: Locale = Locale.getDefault()): DayOfWeek =
        WeekFields.of(locale).firstDayOfWeek

    fun LocalDate.isWeekday(): Boolean =
        dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY

    fun LocalDate.isWeekend(): Boolean =
        dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY

    fun LocalDate.findNextWeekend(): LocalDate {
        return generateSequence(this.plusDays(1)) { it.plusDays(1) }
            .first { it.dayOfWeek == DayOfWeek.SATURDAY || it.dayOfWeek == DayOfWeek.SUNDAY }
    }

    fun LocalDate.findNextWeek(): LocalDate {
        return generateSequence(this.plusDays(1)) { it.plusDays(1) }
            .first { it.dayOfWeek == getFirstDayOfWeek() }
    }
}
