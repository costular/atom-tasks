package com.costular.atomtasks.tasks.helper.recurrence

import com.costular.atomtasks.tasks.model.RecurrenceType

object RecurrenceLookAhead {
    fun numberOfOccurrencesForType(recurrenceType: RecurrenceType): Int {
        return when (recurrenceType) {
            RecurrenceType.DAILY -> 14
            RecurrenceType.WEEKDAYS -> 10
            RecurrenceType.WEEKLY -> 4
            RecurrenceType.MONTHLY -> 4
            RecurrenceType.YEARLY -> 1
        }
    }
}
