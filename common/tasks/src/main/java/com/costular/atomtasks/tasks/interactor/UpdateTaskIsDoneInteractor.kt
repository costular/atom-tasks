package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject

class UpdateTaskIsDoneInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : Interactor<UpdateTaskIsDoneInteractor.Params>() {

    data class Params(
        val taskId: Long,
        val isDone: Boolean,
    )

    override suspend fun doWork(params: Params) {
        tasksRepository.markTask(params.taskId, params.isDone)
    }
}
