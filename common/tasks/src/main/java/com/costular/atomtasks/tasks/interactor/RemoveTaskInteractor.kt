package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.tasks.manager.ReminderManager
import com.costular.atomtasks.tasks.TasksRepository
import javax.inject.Inject

class RemoveTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val reminderManager: ReminderManager,
) : Interactor<RemoveTaskInteractor.Params>() {

    data class Params(val taskId: Long)

    override suspend fun doWork(params: Params) {
        tasksRepository.removeTask(params.taskId)
        reminderManager.cancel(params.taskId)
    }
}
