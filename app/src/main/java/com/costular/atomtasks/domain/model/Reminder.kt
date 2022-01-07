package com.costular.atomtasks.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Reminder(
    val id: Long,
    val time: LocalTime,
    val isEnabled: Boolean,
    val date: LocalDate?
) {

    val isToday: Boolean get() = date == LocalDate.now()
    val isNow: Boolean get() = time.hour == LocalTime.now().hour

}