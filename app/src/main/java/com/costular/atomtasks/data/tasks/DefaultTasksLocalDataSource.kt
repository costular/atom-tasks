package com.costular.atomtasks.data.tasks

import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.Flow

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
            tasksDao.observeAllTasks()
        }
    }

    override fun getTaskById(id: Long): Flow<TaskAggregated> {
        return tasksDao.getTaskById(id)
    }

    override suspend fun getTasksWithReminder(): List<TaskAggregated> {
        return tasksDao.getAllTasks()
            .filter { it.reminder != null }
    }

    override suspend fun removeTask(taskId: Long) {
        tasksDao.removeTaskById(taskId)
    }

    override suspend fun markTask(taskId: Long, isDone: Boolean) {
        tasksDao.updateTaskDone(taskId, isDone)
    }

    override suspend fun updateTaskReminder(taskId: Long, time: LocalTime) {
        reminderDao.updateReminder(taskId, time)
    }
}
