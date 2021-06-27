package com.costular.atomhabits.data.habits

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitLocalDataSource {

    suspend fun createHabit(habitEntity: HabitEntity): Long
    suspend fun createReminderForHabit(reminderEntity: ReminderEntity)
    fun getHabits(day: LocalDate? = null): Flow<List<HabitAggregrated>>
    fun getHabitById(id: Long): Flow<HabitAggregrated>
    suspend fun addHabitRecord(habitId: Long, date: LocalDate)
    suspend fun removeHabitRecord(habitId: Long, date: LocalDate)
    suspend fun removeHabit(habitId: Long)

}