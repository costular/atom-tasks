package com.costular.atomtasks.tasks.repository

import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.RemovalStrategy
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.model.asString
import com.costular.atomtasks.tasks.model.toDomain
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DefaultTasksRepository @Inject constructor(
    private val localDataSource: TaskLocalDataSource,
) : TasksRepository {

    override suspend fun createTask(
        name: String,
        date: LocalDate,
        reminderEnabled: Boolean,
        reminderTime: LocalTime?,
        recurrenceType: RecurrenceType?,
        parentId: Long?,
    ): Long {
        val taskEntity = TaskEntity(
            id = 0,
            createdAt = LocalDate.now(),
            name = name,
            day = date,
            isDone = false,
            recurrenceType = recurrenceType?.asString(),
            isRecurring = recurrenceType != null,
            parentId = parentId,
        )
        val taskId = localDataSource.createTask(taskEntity)

        if (reminderEnabled) {
            localDataSource.createReminderForTask(
                time = requireNotNull(reminderTime),
                date = date,
                reminderEnabled = reminderEnabled,
                taskId = taskId,
            )
        }
        return taskId
    }

    override fun getTaskById(id: Long): Flow<Task> {
        return localDataSource.getTaskById(id).map { it.toDomain() }
    }

    override fun getTasks(day: LocalDate?): Flow<List<Task>> {
        return localDataSource.getTasks(day).map { tasks -> tasks.map { it.toDomain() } }
    }

    override suspend fun getTasksWithReminder(): List<Task> {
        return localDataSource.getTasksWithReminder().map { task -> task.toDomain() }
    }

    override suspend fun removeTask(taskId: Long) {
        localDataSource.removeTask(taskId)
    }

    override suspend fun removeRecurringTask(taskId: Long, removalStrategy: RemovalStrategy) {
        localDataSource.removeRecurringTask(taskId, removalStrategy)
    }

    override suspend fun markTask(taskId: Long, isDone: Boolean) {
        localDataSource.markTask(taskId, isDone)
    }

    override suspend fun updateTaskReminder(
        taskId: Long,
        reminderTime: LocalTime,
        reminderDate: LocalDate,
    ) {
        localDataSource.updateTaskReminder(taskId, reminderTime, reminderDate)
    }

    override suspend fun removeReminder(taskId: Long) {
        localDataSource.removeReminder(taskId)
    }

    override suspend fun updateTask(
        taskId: Long,
        day: LocalDate,
        name: String,
        recurrenceType: RecurrenceType?
    ) {
        localDataSource.updateTask(taskId, day, name, recurrenceType)
    }

    override suspend fun moveTask(day: LocalDate, fromPosition: Int, toPosition: Int) {
        localDataSource.moveTask(day, fromPosition, toPosition)
    }
}
