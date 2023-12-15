package com.costular.atomtasks.tasks.helper.recurrence

import java.time.LocalDate

interface RecurrenceStrategy {
    fun calculateNextOccurrences(
        startDate: LocalDate,
        numberOfOccurrences: Int,
    ): List<LocalDate>
}
