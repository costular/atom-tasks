package com.costular.atomhabits.domain.interactor

import com.costular.atomhabits.data.habits.HabitsRepository
import com.costular.atomhabits.domain.Interactor
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddHabitRecordInteractor @Inject constructor(
    private val habitsRepository: HabitsRepository
) : Interactor<AddHabitRecordInteractor.Params>() {

    data class Params(
        val habitId: Long,
        val date: LocalDate
    )

    override suspend fun doWork(params: Params) {
        habitsRepository.addHabitRecord(params.habitId, params.date)
    }

}