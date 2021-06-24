package com.costular.atomhabits.data.habits

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitLocalDataSource {

    suspend fun createHabit(habitEntity: HabitEntity)
    fun getHabits(day: LocalDate? = null): Flow<List<HabitEntity>>

}