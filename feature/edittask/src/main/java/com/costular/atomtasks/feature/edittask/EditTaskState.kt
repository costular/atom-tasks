package com.costular.atomtasks.feature.edittask

import androidx.compose.runtime.Stable
import com.costular.atomtasks.tasks.model.RecurrenceType
import java.time.LocalDate
import java.time.LocalTime

data class EditTaskState(
    val taskState: TaskState = TaskState.Idle,
    val savingTask: SavingState = SavingState.Uninitialized,
    val taskToSave: TaskToSave? = null,
) {

    companion object {
        val Empty = EditTaskState()
    }
}

sealed class TaskState {

    object Idle : TaskState()

    object Loading : TaskState()

    data class Success(
        val taskId: Long,
        val name: String,
        val date: LocalDate,
        val reminder: LocalTime?,
        val recurrenceType: RecurrenceType?,
    ) : TaskState()
}

@Stable
sealed interface SavingState {

    data object Uninitialized : SavingState
    data object Saving : SavingState
    data object Failure : SavingState
    data object Success : SavingState
}

data class TaskToSave(
    val name: String,
    val date: LocalDate,
    val reminder: LocalTime?,
    val recurrenceType: RecurrenceType?,
)
