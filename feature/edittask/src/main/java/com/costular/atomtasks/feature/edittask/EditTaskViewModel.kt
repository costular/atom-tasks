package com.costular.atomtasks.feature.edittask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.feature.edittask.EditRecurringTaskResponse.THIS
import com.costular.atomtasks.feature.edittask.EditRecurringTaskResponse.THIS_AND_FUTURE_ONES
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.RecurringUpdateStrategy
import com.costular.atomtasks.tasks.usecase.EditTaskUseCase
import com.costular.atomtasks.tasks.usecase.GetTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class EditTaskViewModel @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val editTaskUseCase: EditTaskUseCase,
) : MviViewModel<EditTaskState>(EditTaskState.Empty) {

    fun loadTask(taskId: Long) {
        viewModelScope.launch {
            getTaskByIdUseCase(GetTaskByIdUseCase.Params(taskId))
                .onStart {
                    setState { copy(taskState = TaskState.Loading) }
                }
                .collect { task ->
                    setState {
                        copy(
                            taskState = TaskState.Success(
                                taskId = task.id,
                                name = task.name,
                                date = task.day,
                                reminder = task.reminder?.time,
                                recurrenceType = task.recurrenceType,
                            ),
                        )
                    }
                }
        }
    }

    fun cancelRecurringEdition() {
        setState { copy(taskToSave = null) }
    }

    fun confirmRecurringEdition(response: EditRecurringTaskResponse) {
        val taskToSave = state.value.taskToSave
        requireNotNull(taskToSave) {
            "taskToSave must not be null"
        }

        val recurringUpdateStrategy = when (response) {
            THIS -> RecurringUpdateStrategy.SINGLE
            THIS_AND_FUTURE_ONES -> RecurringUpdateStrategy.SINGLE_AND_FUTURE
        }

        editTask(
            name = taskToSave.name,
            date = taskToSave.date,
            reminder = taskToSave.reminder,
            recurrenceType = taskToSave.recurrenceType,
            recurringUpdateStrategy = recurringUpdateStrategy,
        )
    }

    fun editTask(
        name: String,
        date: LocalDate,
        reminder: LocalTime?,
        recurrenceType: RecurrenceType?,
        recurringUpdateStrategy: RecurringUpdateStrategy?,
    ) {
        viewModelScope.launch {
            val state = state.value
            if (state.taskState !is TaskState.Success) {
                return@launch
            }

            if (state.taskState.recurrenceType != null && recurringUpdateStrategy == null) {
                setState { copy(taskToSave = TaskToSave(name, date, reminder, recurrenceType)) }
                return@launch
            }

            editTaskUseCase(
                EditTaskUseCase.Params(
                    taskId = state.taskState.taskId,
                    name = name,
                    date = date,
                    reminderTime = reminder,
                    recurrenceType = recurrenceType,
                    recurringUpdateStrategy = recurringUpdateStrategy,
                ),
            ).fold(
                ifError = {
                    setState { copy(savingTask = SavingState.Failure) }
                },
                ifResult = {
                    setState { copy(savingTask = SavingState.Success) }
                }
            )
        }
    }
}
