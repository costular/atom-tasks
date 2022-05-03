package com.costular.atomtasks.ui.components.createtask

import java.time.LocalDate
import java.time.LocalTime

data class CreateTaskExpandedState(
    val name: String = "",
    val date: LocalDate = LocalDate.now(),
    val reminder: LocalTime? = null,
    val showSetDate: Boolean = false,
    val showSetReminder: Boolean = false,
) {
    val shouldShowSend: Boolean get() = name.isNotBlank()

    companion object {
        val Empty = CreateTaskExpandedState()
    }
}
