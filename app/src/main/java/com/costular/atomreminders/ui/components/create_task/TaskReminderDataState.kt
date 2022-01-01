package com.costular.atomreminders.ui.components.create_task

data class TaskReminderDataState(
    val isEnabled: Boolean = false,
) {
    companion object {
        val Empty = TaskReminderDataState()
    }
}