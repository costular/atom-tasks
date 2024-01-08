package com.costular.atomtasks.tasks.model

import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import java.lang.IllegalArgumentException

fun TaskAggregated.toDomain(): Task = Task(
    id = task.id,
    name = task.name,
    createdAt = task.createdAt,
    day = task.day,
    reminder = reminder?.toDomain(),
    isDone = task.isDone,
    position = task.position,
    isRecurring = task.isRecurring,
    recurrenceType = task.recurrenceType?.asRecurrenceType(),
    recurrenceEndDate = task.recurrenceEndDate,
    parentId = task.parentId,
)

fun String?.asRecurrenceType(): RecurrenceType = when (this) {
    "daily" -> RecurrenceType.DAILY
    "weekdays" -> RecurrenceType.WEEKDAYS
    "weekly" -> RecurrenceType.WEEKLY
    "monthly" -> RecurrenceType.MONTHLY
    "yearly" -> RecurrenceType.YEARLY
    else -> throw IllegalArgumentException("Unexpected recurrence type")
}

fun RecurrenceType.asString(): String = when (this) {
    RecurrenceType.DAILY -> "daily"
    RecurrenceType.WEEKDAYS -> "weekdays"
    RecurrenceType.WEEKLY -> "weekly"
    RecurrenceType.MONTHLY -> "monthly"
    RecurrenceType.YEARLY -> "yearly"
}

fun ReminderEntity.toDomain(): Reminder = Reminder(
    taskId,
    time,
    date,
)
