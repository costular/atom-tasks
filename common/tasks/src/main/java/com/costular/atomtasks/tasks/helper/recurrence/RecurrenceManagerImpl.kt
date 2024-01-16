package com.costular.atomtasks.tasks.helper.recurrence

import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceLookAhead.numberOfOccurrencesForType
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.tasks.usecase.PopulateRecurringTasksUseCase
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class RecurrenceManagerImpl @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val populateRecurringTasksUseCase: PopulateRecurringTasksUseCase,
) : RecurrenceManager {
    override suspend fun createAheadTasks(date: LocalDate) {
        val tasks = tasksRepository.getTasks(day = date).first()

        tasks.forEach { task ->
            if (!task.isRecurring || task.recurrenceType == null || task.parentId == null) {
                return
            }

            val aheadTasksCountForType = numberOfOccurrencesForType(task.recurrenceType)
            val futureOccurrences = tasksRepository.numberFutureOccurrences(task.parentId, date)

            if (futureOccurrences >= aheadTasksCountForType) {
                return
            }

            populateRecurringTasksUseCase(
                PopulateRecurringTasksUseCase.Params(
                    taskId = task.id,
                    drop = futureOccurrences,
                )
            )
        }
    }
}
