package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.data.SubjectInteractor
import kotlinx.coroutines.flow.Flow

class GetTaskByIdInteractor(
    private val tasksRepository: TasksRepository,
) : SubjectInteractor<GetTaskByIdInteractor.Params, Task>() {

    data class Params(val id: Long)

    override fun createObservable(params: Params): Flow<Task> =
        tasksRepository.getTaskById(params.id)
}
