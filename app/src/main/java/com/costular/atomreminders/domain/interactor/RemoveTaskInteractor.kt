package com.costular.atomreminders.domain.interactor

import com.costular.atomreminders.data.tasks.TasksRepository
import com.costular.atomreminders.domain.Interactor
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