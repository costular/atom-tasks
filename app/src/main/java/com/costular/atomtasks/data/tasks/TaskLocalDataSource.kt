package com.costular.atomtasks.data.tasks

import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface TaskLocalDataSource {

    suspend fun createTask(taskEntity: TaskEntity): Long
    suspend fun createReminderForTask(reminderEntity: ReminderEntity)
    fun getTasks(day: LocalDate? = null): Flow<List<TaskAggregated>>
    fun getTaskById(id: Long): Flow<TaskAggregated>
    suspend fun getTasksWithReminder(): List<TaskAggregated>
    suspend fun removeTask(taskId: Long)
    suspend fun markTask(taskId: Long, isDone: Boolean)
    suspend fun updateTaskReminder(taskId: Long, time: LocalTime)
}
