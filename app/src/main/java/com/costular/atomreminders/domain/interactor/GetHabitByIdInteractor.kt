package com.costular.atomreminders.domain.interactor

import com.costular.atomreminders.data.habits.HabitsRepository
import com.costular.atomreminders.domain.SubjectInteractor
import com.costular.atomreminders.domain.model.Habit
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