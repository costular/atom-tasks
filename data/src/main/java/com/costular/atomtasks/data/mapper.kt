package com.costular.atomtasks.data

import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.Reminder
import com.costular.atomtasks.data.tasks.Task

fun TaskAggregated.toDomain(): Task =
    Task(
        id = task.id,
        name = task.name,
        createdAt = task.createdAt,
        day = task.day,
        reminder = reminder?.toDomain(),
        isDone = task.isDone,
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
