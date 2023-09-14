package com.costular.atomtasks.agenda

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.coreui.date.asDay
import com.costular.atomtasks.tasks.ObserveTasksUseCase
import com.costular.atomtasks.tasks.interactor.MoveTaskUseCase
import com.costular.atomtasks.tasks.interactor.RemoveTaskInteractor
import com.costular.atomtasks.tasks.interactor.UpdateTaskIsDoneInteractor
import com.costular.atomtasks.tasks.manager.AutoforwardManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.collections.immutable.toImmutableList
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
        setState { copy(selectedDay = localDate.asDay(), isHeaderExpanded = false) }
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            observeTasksUseCase(ObserveTasksUseCase.Params(day = state.value.selectedDay.date))
                .onStart { setState { copy(tasks = TasksState.Loading) } }
                .catch { setState { copy(tasks = TasksState.Failure) } }
                .collect { setState { copy(tasks = TasksState.Success(it.toImmutableList())) } }
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

        if (tasks is TasksState.Success) {
            val toIndex = tasks.data.indexOfFirst { it.id == to.key }
            val fromIndex = tasks.data.indexOfFirst { it.id == from.key }

            if (toIndex < 0 || fromIndex < 0) return
            setState {
                copy(
                    tasks = TasksState.Success(
                        tasks.data.toMutableList().apply {
                            add(toIndex, removeAt(fromIndex))
                        }.toImmutableList(),
                    ),
                )
            }
        }
    }

    fun onMoveTask(from: Int, to: Int) {
        viewModelScope.launch {
            val state = state.value

            if (state.tasks is TasksState.Success) {
                moveTaskUseCase(
                    MoveTaskUseCase.Params(
                        day = state.selectedDay.date,
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
