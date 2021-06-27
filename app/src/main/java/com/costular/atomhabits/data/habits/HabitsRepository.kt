package com.costular.atomhabits.data.habits

import com.costular.atomhabits.domain.model.Habit
import com.costular.atomhabits.domain.model.Repetition
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface HabitsRepository {

    suspend fun createHabit(
        name: String,
        repetition: Repetition,
        reminderEnabled: Boolean,
        reminderTime: LocalTime?
    )

    fun getHabitById(id: Long): Flow<Habit>
    fun getHabits(day: LocalDate? = null): Flow<List<Habit>>
    suspend fun addHabitRecord(habitId: Long, date: LocalDate)
    suspend fun removeHabitRecord(habitId: Long, date: LocalDate)
    suspend fun removeHabit(habitId: Long)

}