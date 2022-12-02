package com.costular.atomtasks.agenda

import com.costular.atomtasks.agenda.AgendaViewModel.Companion.DaysAfter
import com.costular.atomtasks.agenda.AgendaViewModel.Companion.DaysBefore
import com.costular.atomtasks.tasks.Task
import com.costular.core.Async
import java.time.LocalDate

data class AgendaState(
    val selectedDay: LocalDate = LocalDate.now(),
    val tasks: Async<List<Task>> = Async.Uninitialized,
    val taskAction: Task? = null,
    val deleteTaskAction: DeleteTaskAction = DeleteTaskAction.Hidden,
    val isHeaderExpanded: Boolean = false,
) {
    companion object {
        val Empty = AgendaState()
    }
}

sealed class DeleteTaskAction {

    object Hidden : DeleteTaskAction()

    data class Shown(
        val taskId: Long,
    ) : DeleteTaskAction()
}
