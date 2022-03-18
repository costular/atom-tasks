package com.costular.atomtasks.ui.components.createtask

data class TaskReminderDataState(
    val isEnabled: Boolean = true,
) {
    companion object {
        val Empty = TaskReminderDataState()
    }
}
