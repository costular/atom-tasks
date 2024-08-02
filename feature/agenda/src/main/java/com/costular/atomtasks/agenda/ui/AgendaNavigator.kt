package com.costular.atomtasks.agenda.ui

interface AgendaNavigator {
    fun navigateToCreateTask(
        date: String,
    )

    fun navigateToEditTask(
        taskId: Long,
    )

    fun navigateToDetailScreenForCreateTask(
        date: String,
    )

    fun navigateToDetailScreenToEdit(
        taskId: Long,
    )

    fun openTaskActions(
        taskId: Long,
        taskName: String,
        isDone: Boolean,
    )
}
