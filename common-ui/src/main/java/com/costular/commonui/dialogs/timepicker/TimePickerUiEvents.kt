package com.costular.commonui.dialogs.timepicker

import java.time.LocalTime

sealed class TimePickerUiEvents : com.costular.atomtasks.coreui.mvi.UiEvent {

    data class Save(val time: LocalTime) : TimePickerUiEvents()

    object Cancel : TimePickerUiEvents()
}
