package com.costular.atomtasks.postponetask.ui

import com.costular.atomtasks.analytics.TrackingEvent
import java.time.LocalDate
import java.time.LocalTime

data class PostponeDefaultOptionClicked(val option: String) : TrackingEvent(
    name = "postpone_default_clicked",
    attributes = mapOf("option" to option)
)

data object PostponeCustomOptionClicked : TrackingEvent(name = "postpone_custom_clicked")

data object PostponeCustomDatePickerOpened :
    TrackingEvent(name = "postpone_custom_date_picker_opened")

data object PostponeCustomTimePickerOpened :
    TrackingEvent(name = "postpone_custom_time_picker_opened")

data class PostponeCustomRescheduled(
    val date: LocalDate,
    val time: LocalTime,
) : TrackingEvent(
    name = "postpone_custom_rescheduled",
    attributes = mapOf(
        "date" to date.toString(),
        "time" to time.toString(),
    )
)
