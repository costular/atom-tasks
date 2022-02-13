package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.Interactor
import com.costular.atomtasks.domain.manager.ReminderManager
import com.costular.atomtasks.domain.repository.TasksRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CreateTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val reminderManager: ReminderManager,
) : Interactor<CreateTaskInteractor.Params>() {

    data class Params(
        val name: String,
        val date: LocalDate,
        val reminderEnabled: Boolean,
        val reminderTime: LocalTime?,
    )

    override suspend fun doWork(params: Params) {
        val taskId = tasksRepository.createTask(
            params.name,
            params.date,
            params.reminderEnabled,
            params.reminderTime
        )

        if (params.reminderEnabled && params.reminderTime != null) {
            reminderManager.set(taskId, params.reminderTime.atDate(params.date))
        }
    }
}
