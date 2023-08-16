package com.costular.atomtasks.tasks

import com.costular.core.usecase.ObservableUseCase
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
