package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.toError
import com.costular.atomtasks.core.toResult
import com.costular.atomtasks.core.usecase.UseCase
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.firstOrNull

class PostponeTaskUseCase @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskReminderInteractor: UpdateTaskReminderInteractor,
    private val taskReminderManager: TaskReminderManager,
    private val editTaskUseCase: EditTaskUseCase,
) : UseCase<PostponeTaskUseCase.Params, Either<PostponeTaskFailure, Unit>> {

    data class Params(
        val taskId: Long,
        val day: LocalDate,
        val time: LocalTime,
    )

    @Suppress("SwallowedException", "TooGenericExceptionCaught", "ReturnCount")
    override suspend fun invoke(params: Params): Either<PostponeTaskFailure, Unit> {
        return try {
            val task = getTaskByIdUseCase(GetTaskByIdUseCase.Params(params.taskId)).firstOrNull()
                ?: return PostponeTaskFailure.Unknown.toError()

            if (task.reminder == null) {
                return PostponeTaskFailure.MissingReminder.toError()
            }

            editTaskUseCase.invoke(
                EditTaskUseCase.Params(
                    taskId = task.id,
                    name = task.name,
                    date = params.day,
                    reminderTime = params.time,
                    recurrenceType = task.recurrenceType,
                    recurringUpdateStrategy = null,
                )
            )
            updateTaskReminderInteractor(
                UpdateTaskReminderInteractor.Params(
                    taskId = params.taskId,
                    time = params.time,
                    date = params.day,
                ),
            )

            taskReminderManager.set(
                taskId = task.id,
                localDateTime = params.day.atTime(params.time)
            )
            Unit.toResult()
        } catch (e: Exception) {
            atomLog { e }
            PostponeTaskFailure.Unknown.toError()
        }
    }
}

sealed interface PostponeTaskFailure {
    data object Unknown : PostponeTaskFailure

    data object MissingReminder : PostponeTaskFailure
}
