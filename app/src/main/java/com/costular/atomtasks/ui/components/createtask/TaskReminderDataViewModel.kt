package com.costular.atomtasks.ui.components.createtask

import com.costular.atomtasks.ui.mvi.MviViewModel

class TaskReminderDataViewModel : MviViewModel<TaskReminderDataState>(TaskReminderDataState.Empty) {

    fun enableReminder(isEnabled: Boolean) {
        setState {
            copy(isEnabled = isEnabled)
        }
    }

    fun toggleEnabled() {
        setState { copy(isEnabled = !isEnabled) }
    }
}
