package com.costular.atomtasks.agenda

import com.costular.atomtasks.coreui.date.Day
import com.costular.atomtasks.coreui.date.asDay
import com.costular.atomtasks.tasks.Task
import java.time.LocalDate
import kotlinx.collections.immutable.ImmutableList

data class AgendaState(
    val selectedDay: Day = LocalDate.now().asDay(),
    val tasks: TasksState = TasksState.Uninitialized,
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

sealed interface TasksState {

    data object Uninitialized : TasksState

    data object Loading : TasksState

    data object Failure : TasksState

    data class Success(
        val data: ImmutableList<Task>
    ): TasksState

}
