package com.costular.atomtasks.domain.interactor

import com.costular.atomtasks.domain.ResultInteractor
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.domain.repository.TasksRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetTasksWithReminderInteractor @Inject constructor(
    private val tasksRepository: TasksRepository,
) : ResultInteractor<Unit, List<Task>>() {

    override suspend fun doWork(params: Unit): List<Task> {
        return tasksRepository.getTasksWithReminder()
    }
}
