package com.costular.atomtasks.agenda

interface AgendaNavigator {
    fun navigateToCreateTask(
        date: String,
        text: String?,
    )
}
