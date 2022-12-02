package com.costular.core.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.time.temporal.WeekFields
import java.util.Locale

object WeekUtil {

    fun getWeekDays(
        localDate: LocalDate,
        firstDayOfWeek: DayOfWeek = getFirstDayOfWeek(),
    ): List<LocalDate> {
        val start = localDate.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
        return (0..6)
            .map { daysToAdd -> start.plusDays(daysToAdd.toLong()) }
            .toList()
    }

    fun getFirstDayOfWeek(locale: Locale = Locale.getDefault()): DayOfWeek =
        WeekFields.of(locale).firstDayOfWeek
}