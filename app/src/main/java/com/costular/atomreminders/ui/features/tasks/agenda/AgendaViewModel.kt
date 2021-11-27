package com.costular.atomreminders.ui.features.tasks.agenda

import androidx.lifecycle.viewModelScope
import com.costular.atomreminders.core.net.DispatcherProvider
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.InvokeStatus
import com.costular.atomreminders.domain.interactor.GetTasksInteractor
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getTasksInteractor: GetTasksInteractor,
) : MviViewModel<AgendaState>(AgendaState()) {

    init {
        loadTasks()
    }

    fun setSelectedDay(localDate: LocalDate) = viewModelScope.launch {
        setState { copy(selectedDay = localDate) }
        loadTasks()
    }

    fun loadTasks() = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }

        getTasksInteractor(GetTasksInteractor.Params(day = state.selectedDay))
        getTasksInteractor.observe()
            .flowOn(dispatcher.io)
            .onStart { setState { copy(habits = Async.Loading) } }
            .catch { setState { copy(habits = Async.Failure(it)) } }
            .collect { setState { copy(habits = Async.Success(it)) } }
    }

    fun onMarkTask(taskId: Long, isDone: Boolean) = viewModelScope.launch {
        val state = withContext(dispatcher.computation) { awaitState() }
        // TODO: update task
    }

    private fun handleMarkStatus(status: InvokeStatus) {
        // TODO: 27/6/21
    }

}