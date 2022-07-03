package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.Interactor
import com.costular.atomtasks.domain.manager.ReminderManager
import com.costular.atomtasks.domain.repository.TasksRepository
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
