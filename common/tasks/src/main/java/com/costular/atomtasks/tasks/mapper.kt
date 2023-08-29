package com.costular.atomtasks.tasks

import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity

fun TaskAggregated.toDomain(): Task = Task(
    id = task.id,
    name = task.name,
    createdAt = task.createdAt,
    day = task.day,
    reminder = reminder?.toDomain(),
    isDone = task.isDone,
    position = task.position,
)

fun ReminderEntity.toDomain(): Reminder = Reminder(
    taskId,
    time,
    isEnabled,
    date,
)

fun Task.toTaskEntity(): TaskEntity = TaskEntity(
    id = id,
    createdAt = createdAt,
    name = name,
    day = day,
    isDone = isDone,
)
