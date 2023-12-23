package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceStrategyFactory
import com.costular.atomtasks.tasks.model.UpdateTaskIsDoneError
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class UpdateTaskIsDoneUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : UseCase<UpdateTaskIsDoneUseCase.Params, Either<UpdateTaskIsDoneError, Unit>> {

    data class Params(
        val taskId: Long,
        val isDone: Boolean,
    )

    override suspend fun invoke(params: Params): Either<UpdateTaskIsDoneError, Unit> {
        return try {
            tasksRepository.markTask(params.taskId, params.isDone)
            Either.Result(Unit)
        } catch (e: Exception) {
            Either.Error(UpdateTaskIsDoneError.UnknownError)
        }
    }
}
