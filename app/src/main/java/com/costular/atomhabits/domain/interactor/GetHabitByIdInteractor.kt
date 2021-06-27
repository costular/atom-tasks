package com.costular.atomhabits.domain.interactor

import com.costular.atomhabits.data.habits.HabitsRepository
import com.costular.atomhabits.domain.SubjectInteractor
import com.costular.atomhabits.domain.model.Habit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHabitByIdInteractor @Inject constructor(
    private val habitsRepository: HabitsRepository
) : SubjectInteractor<GetHabitByIdInteractor.Params, Habit>() {

    data class Params(val id: Long)

    override fun createObservable(params: Params): Flow<Habit> =
        habitsRepository.getHabitById(params.id)

}