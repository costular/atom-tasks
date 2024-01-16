package com.costular.atomtasks.agenda.ui

interface AgendaNavigator {
    fun navigateToCreateTask(
        date: String,
        text: String? = null,
    )

    fun navigateToEditTask(
        taskId: Long,
    )

    fun openTaskActions(
        taskId: Long,
        taskName: String,
        isDone: Boolean,
    )
}
