package com.costular.atomreminders.domain.interactor

import com.costular.atomreminders.data.tasks.TasksRepository
import com.costular.atomreminders.domain.SubjectInteractor
import com.costular.atomreminders.domain.model.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTaskByIdInteractor @Inject constructor(
    private val tasksRepository: TasksRepository
) : SubjectInteractor<GetTaskByIdInteractor.Params, Task>() {

    data class Params(val id: Long)

    override fun createObservable(params: Params): Flow<Task> =
        tasksRepository.getTaskById(params.id)

}