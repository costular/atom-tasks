package com.costular.atomtasks.tasks.helper.recurrence

import com.costular.atomtasks.tasks.model.RecurrenceType

object RecurrenceStrategyFactory {
    fun recurrenceStrategy(recurrenceType: RecurrenceType): RecurrenceStrategy {
        return when (recurrenceType) {
            RecurrenceType.DAILY -> DailyRecurrenceStrategy()
            RecurrenceType.WEEKDAYS -> WeekdaysRecurrenceStrategy()
            RecurrenceType.WEEKLY -> WeeklyRecurrenceStrategy()
            RecurrenceType.MONTHLY -> MonthlyRecurrenceStrategy()
            RecurrenceType.YEARLY -> YearlyRecurrenceStrategy()
        }
    }
}
