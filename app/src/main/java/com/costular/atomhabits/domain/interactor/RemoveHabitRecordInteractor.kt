package com.costular.atomhabits.domain.interactor

import com.costular.atomhabits.data.habits.HabitsRepository
import com.costular.atomhabits.domain.Interactor
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveHabitRecordInteractor @Inject constructor(
    private val habitsRepository: HabitsRepository
) : Interactor<RemoveHabitRecordInteractor.Params>() {

    data class Params(
        val habitId: Long,
        val date: LocalDate
    )

    override suspend fun doWork(params: Params) {
        habitsRepository.removeHabitRecord(params.habitId, params.date)
    }

}