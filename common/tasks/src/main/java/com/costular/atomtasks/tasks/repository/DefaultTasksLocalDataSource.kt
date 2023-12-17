package com.costular.atomtasks.tasks.repository

import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.tasks.model.RemovalStrategy
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Suppress("TooManyFunctions")
internal class DefaultTasksLocalDataSource(
    private val tasksDao: TasksDao,
    private val reminderDao: ReminderDao,
) : TaskLocalDataSource {

    override suspend fun createTask(taskEntity: TaskEntity): Long {
        return tasksDao.createTask(taskEntity)
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

    override suspend fun removeRecurringTask(taskId: Long, removalStrategy: RemovalStrategy) {
        when (removalStrategy) {
            RemovalStrategy.SINGLE -> {
                tasksDao.removeTaskById(taskId)
            }

            RemovalStrategy.ALL -> {
                val parentId = getTaskById(taskId).first().task.parentId ?: -1
                tasksDao.removeAllRecurringTasks(taskId, parentId)
            }

            RemovalStrategy.SINGLE_AND_FUTURE_ONES -> {
                getTaskById(taskId).first().task.parentId?.let {
                    tasksDao.removeFutureRecurringTasks(taskId, it)
                }
            }
        }
    }

    override suspend fun markTask(taskId: Long, isDone: Boolean) {
        tasksDao.updateTaskDone(taskId, isDone)
    }

    override suspend fun updateTaskReminder(taskId: Long, time: LocalTime, date: LocalDate) {
        if (reminderDao.reminderExistForTask(taskId)) {
            reminderDao.updateReminder(taskId, date, time)
        } else {
            createReminderForTask(time, date, true, taskId)
        }
    }

    override suspend fun removeReminder(taskId: Long) {
        reminderDao.removeReminder(taskId)
    }

    override suspend fun updateTask(taskId: Long, day: LocalDate, name: String) {
        val task = tasksDao.getTaskById(taskId).first()
        val oldDay = task.task.day


        if (oldDay != day) {
            val maxPositionForNewDay = tasksDao.getMaxPositionForDate(day) + 1
            tasksDao.updateTask(taskId, day, name, maxPositionForNewDay)
        } else {
            tasksDao.updateTask(taskId, day, name)
        }
    }

    override suspend fun moveTask(day: LocalDate, fromPosition: Int, toPosition: Int) {
        tasksDao.moveTask(day, fromPosition, toPosition)
    }
}
