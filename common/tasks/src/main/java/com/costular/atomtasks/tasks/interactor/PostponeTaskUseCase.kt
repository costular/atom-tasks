package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.toError
import com.costular.atomtasks.core.toResult
import com.costular.atomtasks.core.usecase.UseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PostponeTaskUseCase @Inject constructor(
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val updateTaskReminderInteractor: UpdateTaskReminderInteractor,
    private val taskReminderManager: TaskReminderManager,
    private val updateTaskUseCase: UpdateTaskUseCase,
) : UseCase<PostponeTaskUseCase.Params, Either<PostponeTaskFailure, Unit>> {

    data class Params(
        val taskId: Long,
        val day: LocalDate,
        val time: LocalTime,
    )

    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    override suspend fun invoke(params: Params): Either<PostponeTaskFailure, Unit> {
        return try {
            val task = getTaskByIdUseCase(GetTaskByIdUseCase.Params(params.taskId)).first()

            if (task.reminder == null) {
                return PostponeTaskFailure.MissingReminder.toError()
            }

            updateTaskUseCase.invoke(
                UpdateTaskUseCase.Params(
                    taskId = task.id,
                    name = task.name,
                    date = params.day,
                    reminderTime = params.time,
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
