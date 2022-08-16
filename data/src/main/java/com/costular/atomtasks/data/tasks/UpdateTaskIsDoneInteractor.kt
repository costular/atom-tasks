package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.data.Interactor
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
