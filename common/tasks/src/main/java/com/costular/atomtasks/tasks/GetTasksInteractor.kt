package com.costular.atomtasks.tasks

import com.costular.atomtasks.data.SubjectInteractor
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetTasksInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : SubjectInteractor<GetTasksInteractor.Params, List<Task>>() {

    data class Params(
        val day: LocalDate? = null,
    )

    override fun createObservable(params: Params): Flow<List<Task>> =
        tasksRepository.getTasks(params.day)
}
