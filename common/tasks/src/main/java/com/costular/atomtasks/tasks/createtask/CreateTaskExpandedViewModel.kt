package com.costular.atomtasks.tasks.createtask

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.tasks.interactor.AreExactRemindersAvailable
import com.costular.atomtasks.tasks.model.RecurrenceType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.launch

@Suppress("TooManyFunctions")
@HiltViewModel
class CreateTaskExpandedViewModel @Inject constructor(
    private val areExactRemindersAvailable: AreExactRemindersAvailable
) : MviViewModel<CreateTaskExpandedState>(CreateTaskExpandedState.Empty) {

    fun init() {
        checkIfExactRemindersAreAvailable()
    }

    private fun checkIfExactRemindersAreAvailable() {
        viewModelScope.launch {
            val areExactRemindersEnabled = areExactRemindersAvailable.invoke(Unit)
            setState {
                copy(shouldShowAlarmsRationale = !areExactRemindersEnabled)
            }
        }
    }

    fun setName(name: String) {
        setState {
            copy(name = name)
        }
    }

    fun setDate(localDate: LocalDate) {
        setState {
            copy(date = localDate, showSetDate = false)
        }
    }

    fun setReminder(localTime: LocalTime?) {
        setState {
            copy(
                reminder = localTime,
                showSetReminder = false,
            )
        }
    }

    fun selectDate() {
        setState {
            copy(showSetDate = true)
        }
    }

    fun closeSelectDate() {
        setState {
            copy(showSetDate = false)
        }
    }

    fun selectReminder() {
        setState {
            copy(showSetReminder = true)
        }
    }

    fun closeSelectReminder() {
        setState {
            copy(showSetReminder = false)
        }
    }

    fun clearReminder() {
        setState {
            copy(reminder = null)
        }
    }

    fun selectRecurrence() {
        setState { copy(showSetRecurrence = true) }
    }

    fun closeSelectRecurrence() {
        setState { copy(showSetRecurrence = false) }
    }

    fun setRecurrence(
        recurrenceType: RecurrenceType?
    ) {
        setState { copy(recurrenceType = recurrenceType, showSetRecurrence = false) }
    }

    fun clearRecurrence() {
        setState { copy(recurrenceType = null, showSetRecurrence = false) }
    }

    fun requestSave() {
        sendEvent(
            CreateTaskUiEvents.SaveTask(
                state.value.asCreateTaskResult(),
            ),
        )
        setState {
            CreateTaskExpandedState.Empty
        }
    }

    private fun CreateTaskExpandedState.asCreateTaskResult(): CreateTaskResult =
        CreateTaskResult(
            name = name,
            date = date,
            reminder = reminder,
            recurrenceType = recurrenceType,
        )

    fun navigateToExactAlarmSettings() {
        sendEvent(CreateTaskUiEvents.NavigateToExactAlarmSettings)
    }

    fun exactAlarmSettingChanged() {
        checkIfExactRemindersAreAvailable()
    }
}
