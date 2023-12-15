package com.costular.atomtasks.tasks.helper.recurrence

import java.time.LocalDate

class YearlyRecurrenceStrategy : RecurrenceStrategy {
    override fun calculateNextOccurrences(
        startDate: LocalDate,
        numberOfOccurrences: Int
    ): List<LocalDate> {
        return (1..numberOfOccurrences).map { startDate.plusYears(it.toLong()) }
    }
}
