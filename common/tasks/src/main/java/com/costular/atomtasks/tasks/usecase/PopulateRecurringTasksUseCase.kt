package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.toError
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceLookAhead.numberOfOccurrencesForType
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceStrategyFactory
import com.costular.atomtasks.tasks.model.PopulateRecurringTasksError
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class PopulateRecurringTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : UseCase<PopulateRecurringTasksUseCase.Params, Either<PopulateRecurringTasksError, Unit>> {

    data class Params(
        val taskId: Long,
        val drop: Int? = null,
    )

    override suspend fun invoke(params: Params): Either<PopulateRecurringTasksError, Unit> {
        return try {
            val task = tasksRepository.getTaskById(params.taskId).firstOrNull()
                ?: return PopulateRecurringTasksError.TaskNotFound.toError()

            if (!task.isRecurring || task.recurrenceType == null) {
                Either.Error(PopulateRecurringTasksError.NotRecurringTask)
            } else {
                val recurrenceStrategy = RecurrenceStrategyFactory.recurrenceStrategy(task.recurrenceType)

                val nextDates = recurrenceStrategy.calculateNextOccurrences(
                    startDate = task.day,
                    numberOfOccurrences = numberOfOccurrencesForType(task.recurrenceType),
                    drop = params.drop,
                )

                nextDates.forEach { dayToBeCreated ->
                    tasksRepository.createTask(
                        name = task.name,
                        date = dayToBeCreated,
                        reminderEnabled = task.reminder != null,
                        reminderTime = task.reminder?.time,
                        recurrenceType = task.recurrenceType,
                        parentId = task.parentId ?: task.id,
                    )
                }
                Either.Result(Unit)
            }
        } catch (e: Exception) {
            atomLog { e }
            Either.Error(PopulateRecurringTasksError.UnknownError)
        }
    }
}
