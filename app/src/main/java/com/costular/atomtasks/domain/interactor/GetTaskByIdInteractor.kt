package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.SubjectInteractor
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow

class GetTaskByIdInteractor(
    private val tasksRepository: TasksRepository,
) : SubjectInteractor<GetTaskByIdInteractor.Params, Task>() {

    data class Params(val id: Long)

    override fun createObservable(params: Params): Flow<Task> =
        tasksRepository.getTaskById(params.id)
}
