package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.data.ResultInteractor
import javax.inject.Inject

class GetTasksWithReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ResultInteractor<Unit, List<Task>>() {

    override suspend fun doWork(params: Unit): List<Task> {
        return tasksRepository.getTasksWithReminder()
    }
}
