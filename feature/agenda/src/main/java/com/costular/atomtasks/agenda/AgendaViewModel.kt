package com.costular.atomtasks.agenda

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.CancelDelete
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.CollapseCalendar
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.ConfirmDelete
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.ExpandCalendar
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.MarkTaskAsDone
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.MarkTaskAsNotDone
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.NavigateToDay
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.OrderTask
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.SelectToday
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics.ShowConfirmDeleteDialog
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.coreui.date.asDay
import com.costular.atomtasks.tasks.interactor.MoveTaskUseCase
import com.costular.atomtasks.tasks.interactor.ObserveTasksUseCase
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
    private val atomAnalytics: AtomAnalytics,
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
        atomAnalytics.track(SelectToday)
    }

    fun setSelectedDay(localDate: LocalDate) = viewModelScope.launch {
        setState { copy(selectedDay = localDate.asDay(), isHeaderExpanded = false) }
        loadTasks()
        atomAnalytics.track(NavigateToDay(localDate.toString()))
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
        updateTaskIsDoneInteractor(UpdateTaskIsDoneInteractor.Params(taskId, isDone)).collect()

        val event = if (isDone) {
            MarkTaskAsDone
        } else {
            MarkTaskAsNotDone
        }
        atomAnalytics.track(event)
    }

    fun actionDelete(id: Long) {
        setState {
            copy(deleteTaskAction = DeleteTaskAction.Shown(id))
        }

        atomAnalytics.track(ShowConfirmDeleteDialog)
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            removeTaskInteractor(RemoveTaskInteractor.Params(id))
                .onStart {
                    hideAskDelete()
                }
                .collect()
        }

        atomAnalytics.track(ConfirmDelete)
    }

    fun onDragTask(from: ItemPosition, to: ItemPosition) {
        val data = state.value
        val tasks = data.tasks

        if (tasks is TasksState.Success) {
            val toTask = tasks.data.first { it.id == to.key }
            val fromTask = tasks.data.first { it.id == from.key }

            if (from.index < 0 || to.index < 0) return

            setState {
                copy(
                    fromToPositions = Pair(fromTask.position, toTask.position),
                    tasks = TasksState.Success(
                        tasks.data.toMutableList().apply {
                            add(from.index, removeAt(to.index))
                        }.toImmutableList(),
                    ),
                )
            }
        }
    }

    fun onMoveTask(from: Int, to: Int) {
        viewModelScope.launch {
            val state = state.value

            if (state.tasks is TasksState.Success && state.fromToPositions != null) {
                moveTaskUseCase(
                    MoveTaskUseCase.Params(
                        day = state.selectedDay.date,
                        fromPosition = state.fromToPositions.first,
                        toPosition = state.fromToPositions.second
                    ),
                )
            }

            setState {
                copy(fromToPositions = null)
            }
            atomAnalytics.track(OrderTask)
        }
    }

    fun dismissDelete() {
        hideAskDelete()
        atomAnalytics.track(CancelDelete)
    }

    fun toggleHeader() {
        val state = state.value

        setState {
            copy(isHeaderExpanded = !isHeaderExpanded)
        }

        if (state.isHeaderExpanded) {
            atomAnalytics.track(CollapseCalendar)
        } else {
            atomAnalytics.track(ExpandCalendar)
        }
    }

    fun onEditTask() {
        atomAnalytics.track(AgendaAnalytics.EditTask)
    }

    fun onOpenTaskActions() {
        atomAnalytics.track(AgendaAnalytics.OpenTaskActions)
    }

    private fun hideAskDelete() {
        setState { copy(deleteTaskAction = DeleteTaskAction.Hidden) }
    }

    fun onCreateTask() {
        atomAnalytics.track(AgendaAnalytics.CreateNewTask)
    }
}
