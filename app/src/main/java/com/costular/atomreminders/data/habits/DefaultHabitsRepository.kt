package com.costular.atomreminders.data.habits

import com.costular.atomreminders.data.toDomain
import com.costular.atomreminders.domain.model.Daily
import com.costular.atomreminders.domain.model.Habit
import com.costular.atomreminders.domain.model.Repetition
import com.costular.atomreminders.domain.model.Weekly
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

class DefaultHabitsRepository(
    private val localDataSource: HabitLocalDataSource
) : HabitsRepository {

    override suspend fun createHabit(
        name: String,
        repetition: Repetition,
        reminderEnabled: Boolean,
        reminderTime: LocalTime?
    ) {
        val habitEntity = HabitEntity(
            0,
            LocalDate.now(),
            name,
            repetition.asRepetitionType(),
            repetition.asRepetitionDayOfWeek()
        )
        val habitId = localDataSource.createHabit(habitEntity)

        val reminderDayOfWeek = when (repetition) {
            is Daily -> DayOfWeek.MONDAY.value
            is Weekly -> repetition.dayOfWeek.value
        }

        if (reminderEnabled) {
            val reminder = ReminderEntity(
                0L,
                requireNotNull(reminderTime),
                reminderDayOfWeek,
                reminderEnabled,
                habitId
            )
            localDataSource.createReminderForHabit(reminder)
        }
    }

    override fun getHabitById(id: Long): Flow<Habit> {
        return localDataSource.getHabitById(id).map { it.toDomain() }
    }

    override fun getHabits(day: LocalDate?): Flow<List<Habit>> {
        return localDataSource.getHabits(day).map { habits -> habits.map { it.toDomain() } }
    }

    override suspend fun addHabitRecord(habitId: Long, date: LocalDate) {
        localDataSource.addHabitRecord(habitId, date)
    }

    override suspend fun removeHabitRecord(habitId: Long, date: LocalDate) {
        localDataSource.removeHabitRecord(habitId, date)
    }

    override suspend fun removeHabit(habitId: Long) {
        localDataSource.removeHabit(habitId)
    }

}

private fun Repetition.asRepetitionType(): String = when (this) {
    is Daily -> HabitEntity.REPETITION_TYPE_DAILY
    is Weekly -> HabitEntity.REPETITION_TYPE_WEEKLY
}

private fun Repetition.asRepetitionDayOfWeek(): Int? = when (this) {
    is Daily -> null
    is Weekly -> this.dayOfWeek.value
}