package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.Interactor
import com.costular.atomtasks.domain.repository.TasksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
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
