package com.costular.atomtasks.tasks.createtask

import com.costular.atomtasks.tasks.model.RecurrenceType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class CreateTaskExpandedState(
    val name: String = "",
    val date: LocalDate = LocalDate.now(),
    val reminder: LocalTime? = null,
    val showSetDate: Boolean = false,
    val showSetReminder: Boolean = false,
    val showSetRecurrence: Boolean = false,
    val shouldShowAlarmsRationale: Boolean = false,
    val recurrenceType: RecurrenceType? = null,
    val saving: Boolean = false,
) {
    val shouldShowSend: Boolean
        get() = name.isNotBlank()

    val isReminderError: Boolean
        get() = reminder?.atDate(date)?.isBefore(LocalDateTime.now()) ?: false

    companion object {
        val Empty = CreateTaskExpandedState()
    }
}
