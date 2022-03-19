package com.costular.atomtasks.ui.features.createtask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.domain.InvokeError
import com.costular.atomtasks.domain.InvokeStarted
import com.costular.atomtasks.domain.InvokeSuccess
import com.costular.atomtasks.domain.interactor.CreateTaskInteractor
import com.costular.atomtasks.ui.mvi.MviViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskInteractor: CreateTaskInteractor,
) : MviViewModel<CreateTaskState>(CreateTaskState.Empty) {

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
                )
            )
                .collect { status ->
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
