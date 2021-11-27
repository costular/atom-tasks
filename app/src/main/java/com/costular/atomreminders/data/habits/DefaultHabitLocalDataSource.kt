package com.costular.atomreminders.data.habits

import com.costular.atomreminders.data.habitrecord.HabitRecordDao
import com.costular.atomreminders.data.habitrecord.HabitRecordEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DefaultHabitLocalDataSource(
    private val habitsDao: HabitsDao,
    private val reminderDao: ReminderDao,
    private val habitRecordDao: HabitRecordDao
) : HabitLocalDataSource {

    override suspend fun createHabit(habitEntity: HabitEntity): Long {
        return habitsDao.addHabit(habitEntity)
    }

    override suspend fun createReminderForHabit(reminderEntity: ReminderEntity) {
        reminderDao.insertReminder(reminderEntity)
    }

    override fun getHabits(day: LocalDate?): Flow<List<HabitAggregrated>> {
        return if (day != null) {
            habitsDao.getAllHabitsForDay(day.dayOfWeek.value)
        } else {
            habitsDao.getAllHabits()
        }
    }

    override fun getHabitById(id: Long): Flow<HabitAggregrated> {
        return habitsDao.getHabitById(id)
    }

    override suspend fun addHabitRecord(habitId: Long, date: LocalDate) {
        val entity = HabitRecordEntity(
            0L,
            date,
            habitId
        )
        habitRecordDao.addEntry(entity)
    }

    override suspend fun removeHabitRecord(habitId: Long, date: LocalDate) {
        habitRecordDao.deleteEntryByDate(habitId, date)
    }

    override suspend fun removeHabit(habitId: Long) {
        habitsDao.removeHabitById(habitId)
    }

}