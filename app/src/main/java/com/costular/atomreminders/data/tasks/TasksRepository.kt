package com.costular.atomreminders.data.tasks

import com.costular.atomreminders.domain.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.LocalTime

interface TasksRepository {

    suspend fun createTask(
        name: String,
        date: LocalDate,
        reminderEnabled: Boolean,
        reminderTime: LocalTime?
    )

    fun getTaskById(id: Long): Flow<Task>
    fun getTasks(day: LocalDate? = null): Flow<List<Task>>
    suspend fun removeTask(taskId: Long)

}