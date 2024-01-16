package com.costular.atomtasks.tasks.helper.recurrence

import com.costular.atomtasks.core.util.WeekUtil.isWeekday
import java.time.LocalDate

class WeekdaysRecurrenceStrategy : RecurrenceStrategy {
    override fun calculateNextOccurrences(
        startDate: LocalDate,
        numberOfOccurrences: Int,
        drop: Int?,
    ): List<LocalDate> {
        var daysToReturn = mutableListOf<LocalDate>()
        var date = startDate

        while (daysToReturn.size < numberOfOccurrences) {
            date = date.plusDays(1)
            val isWeekday = date.isWeekday()

            if (isWeekday) {
                daysToReturn.add(date)
            }
        }

        return daysToReturn
            .drop(drop ?: 0)
            .toList()
    }
}
