package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.core.usecase.UseCase
import java.time.LocalDate
import javax.inject.Inject

class MoveTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : UseCase<MoveTaskUseCase.Params, Unit> {

    data class Params(
        val day: LocalDate,
        val fromPosition: Int,
        val toPosition: Int,
    )

    override suspend fun invoke(params: Params) {
        tasksRepository.moveTask(params.day, params.fromPosition, params.toPosition)
    }
}
