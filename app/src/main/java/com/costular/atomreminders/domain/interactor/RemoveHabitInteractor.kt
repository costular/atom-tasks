package com.costular.atomreminders.domain.interactor

import com.costular.atomreminders.data.habits.HabitsRepository
import com.costular.atomreminders.domain.Interactor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveHabitInteractor @Inject constructor(
    private val habitsRepository: HabitsRepository
): Interactor<RemoveHabitInteractor.Params>() {

    data class Params(val habitId: Long)

    override suspend fun doWork(params: Params) {
        habitsRepository.removeHabit(params.habitId)
    }

}