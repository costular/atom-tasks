package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.data.Interactor
import java.time.LocalTime
import javax.inject.Inject

class UpdateTaskReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : Interactor<UpdateTaskReminderInteractor.Params>() {

    data class Params(
        val taskId: Long,
        val time: LocalTime,
    )

    override suspend fun doWork(params: Params) {
        tasksRepository.updateTaskReminder(params.taskId, params.time)
    }
}
