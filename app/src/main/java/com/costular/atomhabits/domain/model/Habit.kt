package com.costular.atomhabits.domain.model

import java.time.LocalDate
import kotlin.random.Random

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