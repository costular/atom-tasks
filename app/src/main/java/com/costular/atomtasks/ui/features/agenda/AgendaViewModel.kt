package com.costular.atomtasks.ui.features.agenda

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.domain.InvokeSuccess
import com.costular.atomtasks.domain.interactor.CreateTaskInteractor
import com.costular.atomtasks.domain.interactor.GetTasksInteractor
import com.costular.atomtasks.domain.interactor.RemoveTaskInteractor
import com.costular.atomtasks.domain.interactor.UpdateTaskIsDoneInteractor
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val createTaskInteractor: CreateTaskInteractor,
    private val getTasksInteractor: GetTasksInteractor,
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor,
    private val removeTaskInteractor: RemoveTaskInteractor,
) : MviViewModel<AgendaState>(AgendaState()) {

    init {
        loadTasks()
    }

    fun createTask(
        name: String,
        date: LocalDate,
        reminder: LocalTime?,
    ) {
        viewModelScope.launch {
            createTaskInteractor(
                CreateTaskInteractor.Params(
                    name,
                    date,
                    reminder != null,
                    reminder
                ))
                .collect { status ->
                    if (status is InvokeSuccess) {
                        sendEvent(AgendaUiEvents.CloseCreateTask)
                    }
                }
        }
    }

    fun setSelectedDay(localDate: LocalDate) = viewModelScope.launch {
        setState { copy(selectedDay = localDate) }
        loadTasks()
    }

    fun loadTasks() = viewModelScope.launch {
        getTasksInteractor(GetTasksInteractor.Params(day = state.value.selectedDay))
        getTasksInteractor.observe()
            .onStart { setState { copy(tasks = Async.Loading) } }
            .catch { setState { copy(tasks = Async.Failure(it)) } }
            .collect { setState { copy(tasks = Async.Success(it)) } }
    }

    fun onMarkTask(taskId: Long, isDone: Boolean) = viewModelScope.launch {
        updateTaskIsDoneInteractor(UpdateTaskIsDoneInteractor.Params(taskId, isDone)).collect()
    }

    fun openTaskAction(task: Task) {
        setState {
            copy(taskAction = task)
        }
    }

    fun dismissTaskAction() {
        setState {
            copy(taskAction = null)
        }
    }

    fun actionDelete(id: Long) {
        setState {
            copy(
                taskAction = null,
                deleteTaskAction = DeleteTaskAction.Shown(id)
            )
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            removeTaskInteractor(RemoveTaskInteractor.Params(id))
                .onStart {
                    hideAskDelete()
                }
                .collect()
        }
    }

    fun dismissDelete() {
        hideAskDelete()
    }

    private fun hideAskDelete() {
        setState { copy(deleteTaskAction = DeleteTaskAction.Hidden) }
    }

    companion object {
        const val DaysBefore = 1
        const val DaysAfter = 30
    }

}