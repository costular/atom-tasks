package com.costular.commonui.dialogs.timepicker

import com.costular.atomtasks.core_ui.mvi.MviViewModel
import java.time.LocalTime

class TimePickerDialogViewModel : com.costular.atomtasks.core_ui.mvi.MviViewModel<TimePickerDialogState>(TimePickerDialogState.Empty) {

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
