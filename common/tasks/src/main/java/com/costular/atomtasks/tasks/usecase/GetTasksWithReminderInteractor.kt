package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.data.ResultInteractor
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.TasksRepository
import javax.inject.Inject

class GetTasksWithReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ResultInteractor<Unit, List<Task>>() {

    override suspend fun doWork(params: Unit): List<Task> {
        return tasksRepository.getTasksWithReminder()
    }
}
