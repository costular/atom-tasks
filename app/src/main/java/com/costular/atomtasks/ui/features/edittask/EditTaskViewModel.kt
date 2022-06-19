package com.costular.atomtasks.ui.features.edittask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.domain.InvokeError
import com.costular.atomtasks.domain.InvokeStarted
import com.costular.atomtasks.domain.InvokeSuccess
import com.costular.atomtasks.domain.interactor.GetTaskByIdInteractor
import com.costular.atomtasks.domain.interactor.UpdateTaskInteractor
import com.costular.atomtasks.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskByIdInteractor: GetTaskByIdInteractor,
    private val updateTaskInteractor: UpdateTaskInteractor,
) : MviViewModel<EditTaskState>(EditTaskState.Empty) {

    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            getTaskByIdInteractor(GetTaskByIdInteractor.Params(taskId))
            getTaskByIdInteractor.flow
                .onStart {
                    setState { copy(taskState = TaskState.Loading) }
                }
                .collect { task ->
                    setState {
                        copy(
                            taskState = TaskState.Success(
                                taskId = task.id,
                                name = task.name,
                                date = task.day,
                                reminder = task.reminder?.time,
                            ),
                        )
                    }
                }
        }
    }

    fun editTask(
        name: String,
        date: LocalDate,
        reminder: LocalTime?,
    ) {
        viewModelScope.launch {
            val state = state.value
            if (state.taskState !is TaskState.Success) {
                return@launch
            }

            updateTaskInteractor(
                UpdateTaskInteractor.Params(
                    taskId = state.taskState.taskId,
                    name = name,
                    date = date,
                    reminderEnabled = reminder != null,
                    reminderTime = reminder,
                ),
            ).collect { status ->
                when (status) {
                    is InvokeStarted -> {
                        setState { copy(savingTask = Async.Loading) }
                    }
                    is InvokeSuccess -> {
                        setState { copy(savingTask = Async.Success(Unit)) }
                    }
                    is InvokeError -> {
                        setState { copy(savingTask = Async.Failure(status.throwable)) }
                    }
                }
            }
        }
    }
}

data class EditTaskState(
    val taskState: TaskState = TaskState.Idle,
    val savingTask: Async<Unit> = Async.Uninitialized,
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
    ) : TaskState()
}
