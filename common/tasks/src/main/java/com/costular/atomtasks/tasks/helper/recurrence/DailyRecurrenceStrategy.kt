package com.costular.atomtasks.tasks.helper.recurrence

import java.time.LocalDate

class DailyRecurrenceStrategy : RecurrenceStrategy {
    override fun calculateNextOccurrences(
        startDate: LocalDate,
        numberOfOccurrences: Int,
        drop: Int?,
    ): List<LocalDate> {
        return (1..numberOfOccurrences)
            .drop(drop ?: 0)
            .map { startDate.plusDays(it.toLong()) }
    }
}
