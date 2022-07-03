package com.costular.commonui.dialogs.timepicker

import com.costular.atomtasks.core_ui.mvi.UiEvent
import java.time.LocalTime

sealed class TimePickerUiEvents : com.costular.atomtasks.core_ui.mvi.UiEvent {

    data class Save(val time: LocalTime) : TimePickerUiEvents()

    object Cancel : TimePickerUiEvents()
}
