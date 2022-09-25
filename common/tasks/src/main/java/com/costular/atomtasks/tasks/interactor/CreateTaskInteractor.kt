package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.tasks.manager.ReminderManager
import com.costular.atomtasks.tasks.TasksRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

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
            params.reminderTime,
        )

        if (params.reminderEnabled && params.reminderTime != null) {
            reminderManager.set(taskId, params.reminderTime.atDate(params.date))
        }
    }
}
