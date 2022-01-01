package com.costular.atomreminders.ui.features.agenda

import androidx.lifecycle.viewModelScope
import androidx.room.Delete
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStarted
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.interactor.CreateTaskInteractor
import com.costular.atomreminders.domain.interactor.GetTasksInteractor
import com.costular.atomreminders.domain.interactor.RemoveTaskInteractor
import com.costular.atomreminders.domain.interactor.UpdateTaskInteractor
import com.costular.atomreminders.domain.model.Task
import com.costular.atomreminders.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val createTaskInteractor: CreateTaskInteractor,
    private val getTasksInteractor: GetTasksInteractor,
    private val updateTaskInteractor: UpdateTaskInteractor,
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
        updateTaskInteractor(UpdateTaskInteractor.Params(taskId, isDone)).collect()
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

    fun actionEdit(id: Long) {
        // TODO: send side effect
    }

    fun dismissDelete() {
        hideAskDelete()
    }

    private fun hideAskDelete() {
        setState { copy(deleteTaskAction = DeleteTaskAction.Hidden) }
    }

    companion object {
        const val DaysBefore = 1
        const val DaysAfter = 14
    }

}