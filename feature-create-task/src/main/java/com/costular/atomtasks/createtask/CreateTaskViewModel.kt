package com.costular.atomtasks.createtask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.domain.interactor.CreateTaskInteractor
import com.costular.core.Async
import com.costular.core.InvokeError
import com.costular.core.InvokeStarted
import com.costular.core.InvokeSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskInteractor: CreateTaskInteractor,
) : com.costular.atomtasks.core_ui.mvi.MviViewModel<CreateTaskState>(CreateTaskState.Empty) {

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
                    reminder,
                ),
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
