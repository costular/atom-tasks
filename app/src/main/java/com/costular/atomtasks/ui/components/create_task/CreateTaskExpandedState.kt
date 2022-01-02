package com.costular.atomtasks.ui.components.create_task

import java.time.LocalDate
import java.time.LocalTime

data class CreateTaskExpandedState(
    val name: String = "",
    val taskDataSelection: TaskDataSelection = TaskDataSelection.None,
    val date: LocalDate = LocalDate.now(),
    val reminder: LocalTime? = null,
) {
    val shouldShowSend: Boolean get() = name.isNotEmpty()
    val shouldShowDateSelection: Boolean get() = taskDataSelection is TaskDataSelection.Date
    val shouldShowReminderSelection: Boolean get() = taskDataSelection is TaskDataSelection.Reminder

    companion object {
        val Empty = CreateTaskExpandedState()
    }

}

sealed class TaskDataSelection {

    object None : TaskDataSelection()

    object Date : TaskDataSelection()

    object Reminder : TaskDataSelection()

}