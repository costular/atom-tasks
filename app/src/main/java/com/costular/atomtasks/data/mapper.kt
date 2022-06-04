package com.costular.atomtasks.data

import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.domain.model.Reminder
import com.costular.atomtasks.domain.model.Task

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
