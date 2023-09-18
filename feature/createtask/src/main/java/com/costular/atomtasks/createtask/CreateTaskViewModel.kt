package com.costular.atomtasks.createtask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.createtask.analytics.CreateTaskAnalytics
import com.costular.atomtasks.tasks.interactor.CreateTaskInteractor
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
    private val atomAnalytics: AtomAnalytics,
) : MviViewModel<CreateTaskState>(CreateTaskState.Uninitialized) {

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
                            setState { CreateTaskState.Loading }
                        }

                        is InvokeSuccess -> {
                            setState { CreateTaskState.Success }
                            atomAnalytics.track(CreateTaskAnalytics.TaskCreated)
                        }

                        is InvokeError -> {
                            setState { CreateTaskState.Failure }
                        }
                    }
                }
        }
    }
}
