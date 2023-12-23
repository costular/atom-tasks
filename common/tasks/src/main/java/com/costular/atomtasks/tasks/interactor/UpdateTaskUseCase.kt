package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.UpdateTaskUseCaseError
import com.costular.atomtasks.tasks.repository.TasksRepository
import java.sql.SQLException
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateTaskUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderManager: TaskReminderManager,
) : UseCase<UpdateTaskUseCase.Params, Either<UpdateTaskUseCaseError, Unit>> {

    data class Params(
        val taskId: Long,
        val name: String,
        val date: LocalDate,
        val reminderTime: LocalTime?,
        val recurrenceType: RecurrenceType?,
    )

    override suspend fun invoke(params: Params): Either<UpdateTaskUseCaseError, Unit> {
        return try {
            with(params) {
                tasksRepository.updateTask(
                    taskId,
                    date,
                    name,
                    recurrenceType,
                )

                if (reminderTime != null) {
                    tasksRepository.updateTaskReminder(taskId, reminderTime, date)
                    taskReminderManager.set(taskId, reminderTime.atDate(date))
                } else {
                    tasksRepository.removeReminder(taskId)
                    taskReminderManager.cancel(taskId)
                }

                Either.Result(Unit)
            }
        } catch (sql: SQLException) {
            atomLog { sql }
            Either.Error(UpdateTaskUseCaseError.UnableToSave)
        } catch (e: Exception) {
            atomLog { e }
            Either.Error(UpdateTaskUseCaseError.UnknownError)
        }
    }
}
