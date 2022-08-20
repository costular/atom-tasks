package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.data.manager.ReminderManager
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
