package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.tasks.manager.TaskReminderManager
import com.costular.atomtasks.tasks.TasksRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateTaskInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
    private val taskReminderManager: TaskReminderManager,
) : Interactor<UpdateTaskInteractor.Params>() {

    data class Params(
        val taskId: Long,
        val name: String,
        val date: LocalDate,
        val reminderEnabled: Boolean,
        val reminderTime: LocalTime?,
    )

    override suspend fun doWork(params: Params) {
        with(params) {
            tasksRepository.updateTask(
                taskId,
                date,
                name,
            )

            if (reminderEnabled && reminderTime != null) {
                tasksRepository.updateTaskReminder(taskId, reminderTime, date)
                taskReminderManager.set(taskId, reminderTime.atDate(date))
            } else {
                tasksRepository.removeReminder(taskId)
                taskReminderManager.cancel(taskId)
            }
        }
    }
}
