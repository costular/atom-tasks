package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.data.tasks.TasksRepository
import com.costular.atomtasks.domain.Interactor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository
): Interactor<RemoveTaskInteractor.Params>() {

    data class Params(val taskId: Long)

    override suspend fun doWork(params: Params) {
        tasksRepository.removeTask(params.taskId)
    }

}