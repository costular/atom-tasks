package com.costular.atomreminders.ui.components.create_task

import com.costular.atomreminders.ui.mvi.MviViewModel

class TaskReminderDataViewModel(

) : MviViewModel<TaskReminderDataState>(TaskReminderDataState.Empty) {

    fun enableReminder(isEnabled: Boolean) {
        setState {
            copy(isEnabled = isEnabled)
        }
    }

    fun toggleEnabled() {
        setState { copy(isEnabled = !isEnabled) }
    }

}