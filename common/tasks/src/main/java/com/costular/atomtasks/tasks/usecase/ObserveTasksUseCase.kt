package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.toError
import com.costular.atomtasks.core.toResult
import com.costular.atomtasks.core.usecase.ObservableUseCase
import com.costular.atomtasks.tasks.model.ObserveTasksError
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.TasksRepository
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class ObserveTasksUseCase @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ObservableUseCase<ObserveTasksUseCase.Params, Either<ObserveTasksError, List<Task>>> {

    data class Params(
        val day: LocalDate? = null,
    )

    override fun invoke(params: Params): Flow<Either<ObserveTasksError, List<Task>>> {
        return try {
            tasksRepository.getTasks(params.day).map {
                it.toResult()
            }
        } catch (e: Exception) {
            atomLog { e }
            flowOf(ObserveTasksError.UnknownError.toError())
        }
    }
}
