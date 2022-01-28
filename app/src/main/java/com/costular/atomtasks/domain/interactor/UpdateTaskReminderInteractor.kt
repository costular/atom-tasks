package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.Interactor
import com.costular.atomtasks.domain.repository.TasksRepository
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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