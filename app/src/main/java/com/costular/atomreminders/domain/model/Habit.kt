package com.costular.atomreminders.domain.model

import java.time.LocalDate

data class Habit(
    val id: Long,
    val name: String,
    val repetition: Repetition,
    val createdAt: LocalDate,
    val reminder: Reminder?,
    val records: List<HabitRecord>
) {

    val isFinishedForToday: Boolean = isFinishedForDate(LocalDate.now())

    fun isFinishedForDate(date: LocalDate): Boolean = records.count { it.date == date } > 0

}