package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.model.RemovalStrategy
import com.costular.atomtasks.tasks.model.RemoveTaskError
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject

class RemoveTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderManager: TaskReminderManager,
) : UseCase<RemoveTaskUseCase.Params, Either<RemoveTaskError, Unit>> {

    data class Params(
        val taskId: Long,
        val strategy: RemovalStrategy? = null,
    )

    override suspend fun invoke(params: Params): Either<RemoveTaskError, Unit> {
        return try {
            if (params.strategy == null) {
                tasksRepository.removeTask(params.taskId)
                taskReminderManager.cancel(params.taskId)
            } else {
                tasksRepository.removeRecurringTask(params.taskId, params.strategy)
            }
            Either.Result(Unit)
        } catch (e: Exception) {
            atomLog { e }
            Either.Error(RemoveTaskError.UnknownError)
        }
    }
}
