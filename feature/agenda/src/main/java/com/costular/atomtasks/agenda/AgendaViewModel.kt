package com.costular.atomtasks.agenda

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.tasks.ObserveTasksUseCase
import com.costular.atomtasks.tasks.interactor.MoveTaskUseCase
import com.costular.atomtasks.tasks.interactor.RemoveTaskInteractor
import com.costular.atomtasks.tasks.interactor.UpdateTaskIsDoneInteractor
import com.costular.atomtasks.tasks.manager.AutoforwardManager
import com.costular.core.Async
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import org.burnoutcrew.reorderable.ItemPosition

@Suppress("TooManyFunctions")
@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor,
    private val removeTaskInteractor: RemoveTaskInteractor,
    private val autoforwardManager: AutoforwardManager,
    private val moveTaskUseCase: MoveTaskUseCase,
) : MviViewModel<AgendaState>(AgendaState()) {

    init {
        loadTasks()
        scheduleAutoforwardTasks()
    }

    private fun scheduleAutoforwardTasks() {
        viewModelScope.launch {
            autoforwardManager.scheduleOrCancelAutoforwardTasks()
        }
    }

    fun setSelectedDayToday() {
        setSelectedDay(LocalDate.now())
    }

    fun setSelectedDay(localDate: LocalDate) = viewModelScope.launch {
        setState { copy(selectedDay = localDate, isHeaderExpanded = false) }
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            observeTasksUseCase(ObserveTasksUseCase.Params(day = state.value.selectedDay))
                .onStart { setState { copy(tasks = Async.Loading) } }
                .catch { setState { copy(tasks = Async.Failure(it)) } }
                .collect { setState { copy(tasks = Async.Success(it)) } }
        }
    }

    fun onMarkTask(taskId: Long, isDone: Boolean) = viewModelScope.launch {
        dismissTaskAction()
        updateTaskIsDoneInteractor(UpdateTaskIsDoneInteractor.Params(taskId, isDone)).collect()
    }

    fun openTaskAction(task: com.costular.atomtasks.tasks.Task) {
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
                deleteTaskAction = DeleteTaskAction.Shown(id),
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

    fun onDragTask(from: ItemPosition, to: ItemPosition) {
        val data = state.value
        val tasks = data.tasks

        if (tasks is Async.Success) {
            val toIndex = tasks.data.indexOfFirst { it.id == to.key }
            val fromIndex = tasks.data.indexOfFirst { it.id == from.key }

            if (toIndex < 0 || fromIndex < 0) return
            setState {
                copy(
                    tasks = Async.Success(
                        tasks.data.toMutableList().apply {
                            add(toIndex, removeAt(fromIndex))
                        },
                    ),
                )
            }
        }
    }

    fun onMoveTask(from: Int, to: Int) {
        viewModelScope.launch {
            val state = state.value

            if (state.tasks is Async.Success) {
                moveTaskUseCase(
                    MoveTaskUseCase.Params(
                        day = state.selectedDay,
                        fromPosition = from + 1,
                        toPosition = to + 1,
                    ),
                )
            }
        }
    }

    fun dismissDelete() {
        hideAskDelete()
    }

    fun toggleHeader() = setState {
        copy(isHeaderExpanded = !isHeaderExpanded)
    }

    private fun hideAskDelete() {
        setState { copy(deleteTaskAction = DeleteTaskAction.Hidden) }
    }
}
