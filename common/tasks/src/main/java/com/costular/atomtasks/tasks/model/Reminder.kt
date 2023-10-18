package com.costular.atomtasks.tasks.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Reminder(
    val id: Long,
    val time: LocalTime,
    val date: LocalDate,
) {
    val isToday: Boolean get() = date == LocalDate.now()
    val localDateTime: LocalDateTime get() = time.atDate(date)
}
