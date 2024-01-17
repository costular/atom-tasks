package com.costular.atomtasks.tasks.helper.recurrence

import java.time.LocalDate

interface RecurrenceStrategy {
    fun calculateNextOccurrences(
        startDate: LocalDate,
        numberOfOccurrences: Int,
        drop: Int? = null,
    ): List<LocalDate>
}
