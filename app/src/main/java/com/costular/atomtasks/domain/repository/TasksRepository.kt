package com.costular.atomtasks.domain.repository

import com.costular.atomtasks.domain.model.Task
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun createTask(
        name: String,
        date: LocalDate,
        reminderEnabled: Boolean,
        reminderTime: LocalTime?,
    ): Long

    fun getTaskById(id: Long): Flow<Task>
    fun getTasks(day: LocalDate? = null): Flow<List<Task>>
    suspend fun getTasksWithReminder(): List<Task>
    suspend fun removeTask(taskId: Long)
    suspend fun markTask(taskId: Long, isDone: Boolean)
    suspend fun updateTaskReminder(taskId: Long, reminderTime: LocalTime)
    suspend fun removeReminder(taskId: Long)
    suspend fun updateTask(taskId: Long, day: LocalDate, name: String)
}
