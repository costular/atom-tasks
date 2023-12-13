package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.manager.TaskReminderManager
import com.costular.atomtasks.tasks.model.CreateTaskError
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.repository.TasksRepository
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderManager: TaskReminderManager,
) : UseCase<CreateTaskUseCase.Params, Either<CreateTaskError, Unit>> {

    data class Params(
        val name: String,
        val date: LocalDate,
        val reminderEnabled: Boolean,
        val reminderTime: LocalTime?,
        val recurrenceType: RecurrenceType?,
    )

    override suspend fun invoke(params: Params): Either<CreateTaskError, Unit> {
        return try {
            val taskId = tasksRepository.createTask(
                name = params.name,
                date = params.date,
                reminderEnabled = params.reminderEnabled,
                reminderTime = params.reminderTime,
                recurrenceType = params.recurrenceType,
            )
            if (params.reminderEnabled && params.reminderTime != null) {
                taskReminderManager.set(taskId, params.reminderTime.atDate(params.date))
            }

            Either.Result(Unit)
        } catch (e: Exception) {
            Either.Error(CreateTaskError.UnknownError)
        }
    }
}
