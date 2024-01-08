package com.costular.atomtasks.tasks.repository

import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceLookAhead
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceLookAhead.numberOfOccurrencesForType
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceStrategyFactory
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.RemovalStrategy
import com.costular.atomtasks.tasks.model.asString
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

@Suppress("TooManyFunctions")
internal class DefaultTasksLocalDataSource @Inject constructor(
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
                val parentId = getTaskById(taskId).first().task.parentId ?: taskId
                tasksDao.removeTaskAndAllOccurrences(taskId, parentId)
            }

            RemovalStrategy.SINGLE_AND_FUTURE_ONES -> {
                getTaskById(taskId).first().task.parentId?.let {
                    tasksDao.removeTaskAndFutureOcurrences(taskId, it)
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

    override suspend fun updateTask(
        taskId: Long,
        day: LocalDate,
        name: String,
        recurrenceType: RecurrenceType?
    ) {
        val task = tasksDao.getTaskById(taskId).first()
        val oldDay = task.task.day
        val newPosition = if (oldDay != day) {
            tasksDao.getMaxPositionForDate(day) + 1
        } else {
            task.task.position
        }
        val wasRecurring = task.task.isRecurring

        if (wasRecurring) {
            if (task.task.isParent) {
                tasksDao.removeChildrenTasks(task.task.id)
            } else {
                tasksDao.removeFutureOccurrencesForRecurringTask(
                    id = taskId,
                    parentId = task.task.parentId ?: taskId
                )
            }
        }

        tasksDao.updateTask(
            taskId = taskId,
            day = day,
            name = name,
            position = newPosition,
            isRecurring = recurrenceType != null,
            recurrence = recurrenceType?.asString(),
        )

        if (wasRecurring) {
            val recurrenceType = requireNotNull(recurrenceType)

            val recurrenceStrategy = RecurrenceStrategyFactory.recurrenceStrategy(recurrenceType)

            val nextDates = recurrenceStrategy.calculateNextOccurrences(
                startDate = day,
                numberOfOccurrences = numberOfOccurrencesForType(recurrenceType),
            )

            nextDates.forEach { dayToBeCreated ->
                val taskId = createTask(
                    TaskEntity(
                        id = 0L,
                        createdAt = LocalDate.now(),
                        name = name,
                        day = dayToBeCreated,
                        isDone = false,
                        position = tasksDao.getMaxPositionForDate(dayToBeCreated) + 1,
                        isRecurring = true,
                        recurrenceType = recurrenceType.asString(),
                        recurrenceEndDate = null,
                        parentId = if (task.task.isParent) task.task.id else task.task.parentId
                    ),
                )

                if (task.reminder != null) {
                    val time = requireNotNull(task.reminder?.time)

                    createReminderForTask(
                        time = time,
                        date = dayToBeCreated,
                        reminderEnabled = true,
                        taskId = taskId,
                    )
                }
            }
        }
    }

    override suspend fun numberFutureOccurrences(parentId: Long, from: LocalDate): Int {
        return tasksDao.countFutureOccurrences(parentId, from)
    }

    override suspend fun moveTask(day: LocalDate, fromPosition: Int, toPosition: Int) {
        tasksDao.moveTask(day, fromPosition, toPosition)
    }
}
