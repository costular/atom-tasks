package com.costular.atomtasks.tasks

import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {

    suspend fun createTask(taskEntity: TaskEntity): Long
    suspend fun createReminderForTask(
        time: LocalTime,
        date: LocalDate,
        reminderEnabled: Boolean,
        taskId: Long,
    )
    fun getTasks(day: LocalDate? = null): Flow<List<TaskAggregated>>
    fun getTaskById(id: Long): Flow<TaskAggregated>
    suspend fun getTasksWithReminder(): List<TaskAggregated>
    suspend fun removeTask(taskId: Long)
    suspend fun markTask(taskId: Long, isDone: Boolean)
    suspend fun updateTaskReminder(taskId: Long, time: LocalTime, date: LocalDate)
    suspend fun removeReminder(taskId: Long)
    suspend fun updateTask(taskId: Long, day: LocalDate, name: String)
}
