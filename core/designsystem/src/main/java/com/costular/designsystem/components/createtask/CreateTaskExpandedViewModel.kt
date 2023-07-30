package com.costular.designsystem.components.createtask

import com.costular.atomtasks.core.ui.mvi.MviViewModel
import java.time.LocalDate
import java.time.LocalTime

class CreateTaskExpandedViewModel :
    MviViewModel<CreateTaskExpandedState>(CreateTaskExpandedState.Empty) {

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
            copy(reminder = localTime, showSetReminder = false)
        }
    }

    fun selectDate() {
        setState {
            copy(showSetDate = true)
        }
    }

    fun closeSelectDate() {
        setState {
            copy(
                showSetDate = false,
            )
        }
    }

    fun selectReminder() {
        setState {
            copy(
                showSetReminder = true,
            )
        }
    }

    fun closeSelectReminder() {
        setState {
            copy(
                showSetReminder = false,
            )
        }
    }

    fun clearReminder() {
        setState {
            copy(reminder = null)
        }
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
        )
}
