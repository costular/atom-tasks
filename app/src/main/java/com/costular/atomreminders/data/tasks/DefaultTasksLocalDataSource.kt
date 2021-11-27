package com.costular.atomreminders.data.tasks

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class DefaultTasksLocalDataSource(
    private val tasksDao: TasksDao,
    private val reminderDao: ReminderDao,
) : TaskLocalDataSource {

    override suspend fun createTask(taskEntity: TaskEntity): Long {
        return tasksDao.addTask(taskEntity)
    }

    override suspend fun createReminderForTask(reminderEntity: ReminderEntity) {
        reminderDao.insertReminder(reminderEntity)
    }

    override fun getTasks(day: LocalDate?): Flow<List<TaskAggregated>> {
        return if (day != null) {
            tasksDao.getAllTasksForDate(day)
        } else {
            tasksDao.getAllTasks()
        }
    }

    override fun getTaskById(id: Long): Flow<TaskAggregated> {
        return tasksDao.getTaskById(id)
    }

    override suspend fun removeTask(taskId: Long) {
        tasksDao.removeTaskById(taskId)
    }

}