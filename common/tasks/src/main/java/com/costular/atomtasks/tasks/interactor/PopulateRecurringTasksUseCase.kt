package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceStrategyFactory
import com.costular.atomtasks.tasks.model.PopulateRecurringTasksError
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PopulateRecurringTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : UseCase<PopulateRecurringTasksUseCase.Params, Either<PopulateRecurringTasksError, Unit>> {

    data class Params(
        val taskId: Long,
    )

    override suspend fun invoke(params: Params): Either<PopulateRecurringTasksError, Unit> {
        return try {
            val task = tasksRepository.getTaskById(params.taskId).first()

            if (!task.isRecurring || task.recurrenceType == null) {
                return Either.Error(PopulateRecurringTasksError.NotRecurringTask)
            }

            val recurrenceStrategy =
                RecurrenceStrategyFactory.recurrenceStrategy(task.recurrenceType)

            val nextDates = recurrenceStrategy.calculateNextOccurrences(
                startDate = task.day,
                numberOfOccurrences = numberOfOccurrencesForType(task.recurrenceType)
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
        } catch (e: Exception) {
            atomLog { e }
            Either.Error(PopulateRecurringTasksError.UnknownError)
        }
    }

    private fun numberOfOccurrencesForType(recurrenceType: RecurrenceType): Int {
        return when (recurrenceType) {
            RecurrenceType.DAILY -> 14
            RecurrenceType.WEEKDAYS -> 10
            RecurrenceType.WEEKLY -> 4
            RecurrenceType.MONTHLY -> 4
            RecurrenceType.YEARLY -> 1
        }
    }
}
