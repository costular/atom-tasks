package com.costular.atomtasks.tasks.repository

import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.RemovalStrategy
import com.costular.atomtasks.tasks.model.Task
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.Flow

interface TasksRepository {

    suspend fun createTask(
        name: String,
        date: LocalDate,
        reminderEnabled: Boolean,
        reminderTime: LocalTime?,
        recurrenceType: RecurrenceType?,
        parentId: Long?,
    ): Long

    fun getTaskById(id: Long): Flow<Task>
    fun getTasks(day: LocalDate? = null): Flow<List<Task>>
    suspend fun getTasksWithReminder(): List<Task>
    suspend fun removeTask(taskId: Long)
    suspend fun removeRecurringTask(taskId: Long, removalStrategy: RemovalStrategy)
    suspend fun markTask(taskId: Long, isDone: Boolean)
    suspend fun updateTaskReminder(taskId: Long, reminderTime: LocalTime, reminderDate: LocalDate)
    suspend fun removeReminder(taskId: Long)
    suspend fun updateTask(taskId: Long, day: LocalDate, name: String)
    suspend fun moveTask(day: LocalDate, fromPosition: Int, toPosition: Int)
}
