package com.costular.atomhabits.domain.interactor

import com.costular.atomhabits.data.habits.HabitsRepository
import com.costular.atomhabits.domain.SubjectInteractor
import com.costular.atomhabits.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHabitsInteractor @Inject constructor(
    private val habitsRepository: HabitsRepository
) : SubjectInteractor<GetHabitsInteractor.Params, List<Habit>>() {

    data class Params(
        val day: LocalDate? = null
    )

    override fun createObservable(params: Params): Flow<List<Habit>> =
        habitsRepository.getHabits(params.day)

}