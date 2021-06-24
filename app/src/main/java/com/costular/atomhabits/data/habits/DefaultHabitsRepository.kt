package com.costular.atomhabits.data.habits

import com.costular.atomhabits.data.toDomain
import com.costular.atomhabits.domain.model.Daily
import com.costular.atomhabits.domain.model.Habit
import com.costular.atomhabits.domain.model.Repetition
import com.costular.atomhabits.domain.model.Weekly
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class DefaultHabitsRepository(
    private val localDataSource: HabitLocalDataSource
) : HabitsRepository {

    override suspend fun createHabit(name: String, repetition: Repetition) {
        val habitEntity = HabitEntity(
            0,
            LocalDate.now(),
            name,
            repetition.asRepetitionType(),
            repetition.asRepetitionDayOfWeek()
        )
        localDataSource.createHabit(habitEntity)
    }

    override fun getHabits(day: LocalDate?): Flow<List<Habit>> {
        return localDataSource.getHabits(day).map { habits -> habits.map { it.toDomain() } }
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