package com.costular.atomreminders.ui.features.tasks.detail

import androidx.lifecycle.viewModelScope
import com.costular.atomreminders.core.net.DispatcherProvider
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.InvokeError
import com.costular.atomreminders.domain.InvokeStarted
import com.costular.atomreminders.domain.InvokeSuccess
import com.costular.atomreminders.domain.interactor.GetTaskByIdInteractor
import com.costular.atomreminders.domain.interactor.RemoveTaskInteractor
import com.costular.decorit.presentation.base.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val getTaskByIdInteractor: GetTaskByIdInteractor,
    private val removeTaskInteractor: RemoveTaskInteractor
) : MviViewModel<TaskDetailState>(TaskDetailState()) {

    fun load(taskId: Long) = viewModelScope.launch {
        getTaskByIdInteractor(GetTaskByIdInteractor.Params(taskId))
        getTaskByIdInteractor.observe()
            .flowOn(dispatcher.io)
            .onStart { setState { copy(task = Async.Loading) } }
            .catch {
                Timber.e(it)
                setState { copy(task = Async.Failure(it)) }
            }
            .collect { habit ->
                setState { copy(task = Async.Success(habit)) }
            }
    }

    fun delete() = viewModelScope.launch {
        val task = state.value.task

        if (task is Async.Success) {
            removeTaskInteractor(RemoveTaskInteractor.Params(task().id))
                .flowOn(dispatcher.io)
                .collect { status ->
                    when (status) {
                        is InvokeStarted -> {

                        }
                        is InvokeSuccess -> {
                            sendEvent(TaskDetailEvents.GoBack)
                        }
                        is InvokeError -> {
                            // TODO: 27/6/21
                        }
                    }
                }
        }
    }

}