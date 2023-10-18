package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.SubjectInteractor
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTaskByIdInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : SubjectInteractor<GetTaskByIdInteractor.Params, Task>() {

    data class Params(val id: Long)

    override fun createObservable(params: Params): Flow<Task> =
        tasksRepository.getTaskById(params.id)
}
