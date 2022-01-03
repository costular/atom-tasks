package com.costular.atomtasks.ui.components.create_task

data class TaskReminderDataState(
    val isEnabled: Boolean = true,
) {
    companion object {
        val Empty = TaskReminderDataState()
    }
}