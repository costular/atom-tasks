package com.costular.atomtasks.ui.dialogs.timepicker

import com.costular.atomtasks.ui.mvi.MviViewModel
import java.time.LocalTime

class TimePickerDialogViewModel : MviViewModel<TimePickerDialogState>(TimePickerDialogState.Empty) {

    fun setTime(time: LocalTime) {
        setState {
            copy(time = time)
        }
    }

    fun cancel() {
        sendEvent(TimePickerUiEvents.Cancel)
    }

    fun save() {
        withState {
            sendEvent(TimePickerUiEvents.Save(time))
        }
    }
}
