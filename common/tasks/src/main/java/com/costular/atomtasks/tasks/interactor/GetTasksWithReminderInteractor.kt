package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.ResultInteractor
import com.costular.atomtasks.tasks.Task
import com.costular.atomtasks.tasks.TasksRepository
import javax.inject.Inject

class GetTasksWithReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ResultInteractor<Unit, List<Task>>() {

    override suspend fun doWork(params: Unit): List<Task> {
        return tasksRepository.getTasksWithReminder()
    }
}
