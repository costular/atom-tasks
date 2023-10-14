package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.tasks.manager.TaskReminderManager
import com.costular.core.Either
import com.costular.core.toError
import com.costular.core.toResult
import com.costular.core.usecase.UseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PostponeTaskUseCase @Inject constructor(
    private val getTaskByIdInteractor: GetTaskByIdInteractor,
    private val updateTaskReminderInteractor: UpdateTaskReminderInteractor,
    private val taskReminderManager: TaskReminderManager,
) : UseCase<PostponeTaskUseCase.Params, Either<PostponeTaskFailure, Unit>> {

    data class Params(
        val taskId: Long,
        val day: LocalDate,
        val time: LocalTime,
    )

    override suspend fun invoke(params: Params): Either<PostponeTaskFailure, Unit> {
        return try {
            getTaskByIdInteractor(GetTaskByIdInteractor.Params(params.taskId))
            val task = getTaskByIdInteractor.flow.first()

            if (task.reminder == null || !task.reminder.isEnabled) {
                return PostponeTaskFailure.MissingReminder.toError()
            }

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
            PostponeTaskFailure.Unknown.toError()
        }

    }
}

sealed interface PostponeTaskFailure {
    data object Unknown : PostponeTaskFailure

    data object MissingReminder : PostponeTaskFailure
}
