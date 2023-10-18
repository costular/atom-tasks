package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.tasks.manager.TaskReminderManager
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject

class RemoveTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderManager: TaskReminderManager,
) : Interactor<RemoveTaskInteractor.Params>() {

    data class Params(val taskId: Long)

    override suspend fun doWork(params: Params) {
        tasksRepository.removeTask(params.taskId)
        taskReminderManager.cancel(params.taskId)
    }
}
