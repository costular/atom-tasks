package com.costular.atomreminders.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class Reminder(
    val id: Long,
    val time: LocalTime,
    val isEnabled: Boolean,
    val date: LocalDate?
)