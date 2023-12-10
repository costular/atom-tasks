package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.core.usecase.UseCase
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTaskByIdUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : UseCase<GetTaskByIdUseCase.Params, Flow<Task>> {

    data class Params(val id: Long)

    override suspend fun invoke(params: Params): Flow<Task> =
        tasksRepository.getTaskById(params.id)
}
