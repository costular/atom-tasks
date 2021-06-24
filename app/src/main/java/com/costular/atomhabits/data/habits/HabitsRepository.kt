package com.costular.atomhabits.data.habits

import com.costular.atomhabits.domain.model.Habit
import com.costular.atomhabits.domain.model.Repetition
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface HabitsRepository {

    suspend fun createHabit(
        name: String,
        repetition: Repetition
    )

    fun getHabits(day: LocalDate? = null): Flow<List<Habit>>

}