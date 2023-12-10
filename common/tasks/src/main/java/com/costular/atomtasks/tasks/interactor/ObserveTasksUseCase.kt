package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.core.usecase.ObservableUseCase
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class ObserveTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ObservableUseCase<ObserveTasksUseCase.Params, List<Task>> {

    data class Params(
        val day: LocalDate? = null,
    )

    override fun invoke(params: Params): Flow<List<Task>> = tasksRepository.getTasks(params.day)
}
