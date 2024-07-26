package com.atomtasks.feature.detail

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.atomtasks.feature.detail.EditRecurringTaskResponse.THIS
import com.atomtasks.feature.detail.EditRecurringTaskResponse.THIS_AND_FUTURE_ONES
import com.atomtasks.feature.detail.destinations.TaskDetailScreenDestination
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.RecurringUpdateStrategy
import com.costular.atomtasks.tasks.usecase.AreExactRemindersAvailable
import com.costular.atomtasks.tasks.usecase.CreateTaskUseCase
import com.costular.atomtasks.tasks.usecase.EditTaskUseCase
import com.costular.atomtasks.tasks.usecase.GetTaskByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val areExactRemindersAvailable: AreExactRemindersAvailable,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val createTaskUseCase: CreateTaskUseCase,
    private val atomAnalytics: AtomAnalytics,
) : MviViewModel<TaskDetailUiState>(TaskDetailUiState()) {

    private val navArgs: TaskDetailNavArgs = TaskDetailScreenDestination.argsFrom(savedStateHandle)

    init {
        checkIfExactRemindersAreAvailable()

        if (navArgs.taskId != null) {
            loadTask(navArgs.taskId)
        } else {
            setState { copy(date = requireNotNull(navArgs.defaultDate)) }
        }
    }

    private fun loadTask(taskId: Long) {
        viewModelScope.launch {
            getTaskByIdUseCase(GetTaskByIdUseCase.Params(taskId))
                .collect { task ->
                    setState {
                        copy(
                            taskId = task.id,
                            name = TextFieldState(task.name),
                            date = task.day,
                            reminder = task.reminder?.time,
                            recurrenceType = task.recurrenceType,
                        )
                    }
                }
        }
    }

    private fun checkIfExactRemindersAreAvailable() {
        viewModelScope.launch {
            val areExactRemindersEnabled = areExactRemindersAvailable.invoke(Unit)
            setState {
                copy(shouldShowExactAlarmRationale = !areExactRemindersEnabled)
            }
        }
    }

    fun onDateChanged(localDate: LocalDate) {
        setState {
            copy(
                date = localDate,
                showSetDate = false
            )
        }
    }

    fun onReminderChanged(localTime: LocalTime?) {
        setState {
            copy(
                reminder = localTime,
                showSetReminder = false,
            )
        }
    }

    fun onSelectDateClicked() {
        atomAnalytics.track(DetailAnalytics.DatePickerOpened)
        setState {
            copy(showSetDate = true)
        }
    }

    fun closeSelectDate() {
        setState {
            copy(showSetDate = false)
        }
    }

    fun onSelectReminderClicked() {
        atomAnalytics.track(DetailAnalytics.ReminderPickerOpened)
        setState {
            copy(showSetReminder = true)
        }
    }

    fun closeSelectReminder() {
        setState {
            copy(showSetReminder = false)
        }
    }

    fun onReminderRemoved() {
        setState {
            copy(reminder = null)
        }
    }

    fun onSelectRecurrenceClicked() {
        atomAnalytics.track(DetailAnalytics.RecurrencePickerOpened)
        setState { copy(showSetRecurrence = true) }
    }

    fun closeSelectRecurrence() {
        setState { copy(showSetRecurrence = false) }
    }

    fun onRecurrenceChanged(
        recurrenceType: RecurrenceType?
    ) {
        setState { copy(recurrenceType = recurrenceType, showSetRecurrence = false) }
    }

    fun clearRecurrence() {
        setState { copy(recurrenceType = null, showSetRecurrence = false) }
    }

    fun save() {
        setState {
            copy(isSaving = true)
        }

        if (state.value.isEditMode) {
            editTask(recurringUpdateStrategy = null)
        } else {
            createTask()
        }
    }

    fun navigateToExactAlarmSettings() {
        // sendEvent(CreateTaskUiEvents.NavigateToExactAlarmSettings)
    }

    fun exactAlarmSettingChanged() {
        checkIfExactRemindersAreAvailable()
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
            recurringUpdateStrategy = recurringUpdateStrategy,
        )
    }

    private fun createTask() {
        viewModelScope.launch {
            val currentState = state.value

            createTaskUseCase(
                CreateTaskUseCase.Params(
                    name = currentState.name.text.toString(),
                    date = currentState.date,
                    reminderEnabled = currentState.reminder != null,
                    reminderTime = currentState.reminder,
                    recurrenceType = currentState.recurrenceType,
                ),
            ).fold(
                ifError = {
                    // TODO: Handle error
                },
                ifResult = {
                    atomAnalytics.track(DetailAnalytics.TaskCreated)
                    sendEvent(TaskDetailUiEvent.Close)
                }
            )
        }
    }

    fun editTask(
        recurringUpdateStrategy: RecurringUpdateStrategy?,
    ) {
        viewModelScope.launch {
            val state = state.value

            if (state.recurrenceType != null && recurringUpdateStrategy == null) {
                setState {
                    copy(
                        taskToSave = TaskToSave(
                            name = name.text.toString(),
                            date = date,
                            reminder = reminder,
                            recurrenceType = recurrenceType
                        )
                    )
                }
                return@launch
            }

            editTaskUseCase(
                EditTaskUseCase.Params(
                    taskId = requireNotNull(state.taskId),
                    name = state.name.text.toString(),
                    date = state.date,
                    reminderTime = state.reminder,
                    recurrenceType = state.recurrenceType,
                    recurringUpdateStrategy = recurringUpdateStrategy,
                ),
            ).fold(
                ifError = {
                    // TODO failure
                },
                ifResult = {
                    atomAnalytics.track(DetailAnalytics.TaskEdited)
                    sendEvent(TaskDetailUiEvent.Close)
                }
            )
        }
    }

    fun onClose() {
        atomAnalytics.track(DetailAnalytics.Closed)
        sendEvent(TaskDetailUiEvent.Close)
    }
}