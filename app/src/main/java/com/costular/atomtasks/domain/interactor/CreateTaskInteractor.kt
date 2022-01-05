package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.repository.TasksRepository
import com.costular.atomtasks.domain.Interactor
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository
) : Interactor<CreateTaskInteractor.Params>() {

    data class Params(
        val name: String,
        val date: LocalDate,
        val reminderEnabled: Boolean,
        val reminderTime: LocalTime?
    )

    override suspend fun doWork(params: Params) {
        tasksRepository.createTask(
            params.name,
            params.date,
            params.reminderEnabled,
            params.reminderTime
        )
    }


}