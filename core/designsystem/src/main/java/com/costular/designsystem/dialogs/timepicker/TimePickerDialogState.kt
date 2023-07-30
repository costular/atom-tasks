package com.costular.designsystem.dialogs.timepicker

import java.time.LocalTime

data class TimePickerDialogState(
    val time: LocalTime = LocalTime.now(),
) {

    companion object {
        val Empty = TimePickerDialogState()
    }
}
