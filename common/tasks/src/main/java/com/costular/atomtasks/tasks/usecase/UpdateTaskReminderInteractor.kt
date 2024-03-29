package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.core.usecase.UseCase
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class UpdateTaskReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : UseCase<UpdateTaskReminderInteractor.Params, Unit> {

    data class Params(
        val taskId: Long,
        val time: LocalTime,
        val date: LocalDate,
    )

    override suspend fun invoke(params: Params) {
        tasksRepository.updateTaskReminder(params.taskId, params.time, params.date)
    }
}
