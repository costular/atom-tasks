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

    override suspend fun createReminderForTask(
        time: LocalTime,
        date: LocalDate,
        reminderEnabled: Boolean,
        taskId: Long,
    ) {
        val reminder = ReminderEntity(
            reminderId = 0L,
            time = time,
            date = date,
            isEnabled = reminderEnabled,
            taskId = taskId,
        )
        reminderDao.insertReminder(reminder)
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

    override suspend fun updateTaskReminder(taskId: Long, time: LocalTime, date: LocalDate) {
        if (reminderDao.reminderExistForTask(taskId)) {
            reminderDao.updateReminder(taskId, time)
        } else {
            createReminderForTask(time, date, true, taskId)
        }
    }

    override suspend fun removeReminder(taskId: Long) {
        reminderDao.removeReminder(taskId)
    }

    override suspend fun updateTask(taskId: Long, day: LocalDate, name: String) {
        tasksDao.updateTask(taskId, day, name)
    }
}
