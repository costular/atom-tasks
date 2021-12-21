package com.costular.atomreminders.ui.features.agenda

import androidx.lifecycle.viewModelScope
import com.costular.atomreminders.core.net.DispatcherProvider
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStatus
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.interactor.GetTasksInteractor
import com.costular.atomreminders.domain.interactor.UpdateTaskInteractor
import com.costular.atomreminders.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getTasksInteractor: GetTasksInteractor,
    private val updateTaskInteractor: UpdateTaskInteractor,
) : MviViewModel<AgendaState>(AgendaState()) {

    init {
        loadTasks()
    }

    fun setSelectedDay(localDate: LocalDate) = viewModelScope.launch {
        setState { copy(selectedDay = localDate) }
        loadTasks()
    }

    fun loadTasks() = viewModelScope.launch {
        getTasksInteractor(GetTasksInteractor.Params(day = state.value.selectedDay))
        getTasksInteractor.observe()
            .onStart { setState { copy(habits = Async.Loading) } }
            .catch { setState { copy(habits = Async.Failure(it)) } }
            .collect { setState { copy(habits = Async.Success(it)) } }
    }

    fun onMarkTask(taskId: Long, isDone: Boolean) = viewModelScope.launch {
        updateTaskInteractor(UpdateTaskInteractor.Params(taskId, isDone))
            .collect { status ->
                when (status) {
                    is InvokeSuccess -> {
                        loadTasks()
                    }
                    is InvokeError -> {
                        // TODO: show error marking task
                    }
                }
            }
    }

}