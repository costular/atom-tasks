package com.costular.atomhabits.data.habits

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DefaultHabitLocalDataSource(
    private val habitsDao: HabitsDao
) : HabitLocalDataSource {

    override suspend fun createHabit(habitEntity: HabitEntity) {
        habitsDao.addHabit(habitEntity)
    }

    override fun getHabits(day: LocalDate?): Flow<List<HabitEntity>> {
        return if (day != null) {
            habitsDao.getAllHabitsForDay(day.dayOfWeek.value)
        } else {
            habitsDao.getAllHabits()
        }
    }

}