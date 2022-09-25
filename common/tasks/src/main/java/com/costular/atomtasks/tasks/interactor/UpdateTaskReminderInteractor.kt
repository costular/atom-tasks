package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.Interactor
import com.costular.atomtasks.tasks.TasksRepository
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class UpdateTaskReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : Interactor<UpdateTaskReminderInteractor.Params>() {

    data class Params(
        val taskId: Long,
        val time: LocalTime,
        val date: LocalDate,
    )

    override suspend fun doWork(params: Params) {
        tasksRepository.updateTaskReminder(params.taskId, params.time, params.date)
    }
}
