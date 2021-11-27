package com.costular.atomreminders.domain.interactor

import com.costular.atomreminders.data.habits.HabitsRepository
import com.costular.atomreminders.domain.Interactor
import com.costular.atomreminders.domain.model.Repetition
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateHabitInteractor @Inject constructor(
    private val habitsRepository: HabitsRepository
) : Interactor<CreateHabitInteractor.Params>() {

    data class Params(
        val name: String,
        val repetition: Repetition,
        val reminderEnabled: Boolean,
        val reminderTime: LocalTime?
    )

    override suspend fun doWork(params: Params) {
        habitsRepository.createHabit(
            params.name,
            params.repetition,
            params.reminderEnabled,
            params.reminderTime
        )
    }


}