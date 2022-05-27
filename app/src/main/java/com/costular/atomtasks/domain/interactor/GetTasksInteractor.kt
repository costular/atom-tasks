package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.SubjectInteractor
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.domain.repository.TasksRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : SubjectInteractor<GetTasksInteractor.Params, List<Task>>() {

    data class Params(
        val day: LocalDate? = null,
    )

    override fun createObservable(params: Params): Flow<List<Task>> =
        tasksRepository.getTasks(params.day)
}
