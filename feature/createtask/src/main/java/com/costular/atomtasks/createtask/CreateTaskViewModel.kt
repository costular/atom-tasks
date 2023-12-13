package com.costular.atomtasks.createtask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.createtask.analytics.CreateTaskAnalytics
import com.costular.atomtasks.tasks.interactor.CreateTaskUseCase
import com.costular.atomtasks.tasks.model.RecurrenceType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase,
    private val atomAnalytics: AtomAnalytics,
) : MviViewModel<CreateTaskState>(CreateTaskState.Uninitialized) {

    fun createTask(
        name: String,
        date: LocalDate,
        reminder: LocalTime?,
        recurrence: RecurrenceType?,
    ) {
        viewModelScope.launch {
            setState { CreateTaskState.Saving }

            createTaskUseCase(
                CreateTaskUseCase.Params(
                    name = name,
                    date = date,
                    reminderEnabled = reminder != null,
                    reminderTime = reminder,
                    recurrenceType = recurrence,
                ),
            ).fold(
                ifError = {
                    setState { CreateTaskState.Failure }
                },
                ifResult = {
                    atomAnalytics.track(CreateTaskAnalytics.TaskCreated)
                    setState { CreateTaskState.Success }
                }
            )
        }
    }
}
