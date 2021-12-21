package com.costular.atomreminders.domain.interactor

import com.costular.atomreminders.data.tasks.TasksRepository
import com.costular.atomreminders.domain.Interactor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : Interactor<UpdateTaskInteractor.Params>() {

    data class Params(
        val taskId: Long,
        val isDone: Boolean,
    )

    override suspend fun doWork(params: Params) {
        tasksRepository.markTask(params.taskId, params.isDone)
    }

}