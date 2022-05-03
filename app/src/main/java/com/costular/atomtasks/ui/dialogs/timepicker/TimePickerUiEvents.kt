package com.costular.atomtasks.ui.dialogs.timepicker

import com.costular.decorit.presentation.base.UiEvent
import java.time.LocalTime

sealed class TimePickerUiEvents : UiEvent {

    data class Save(val time: LocalTime) : TimePickerUiEvents()

    object Cancel : TimePickerUiEvents()
}
