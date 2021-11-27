package com.costular.atomreminders.data

import com.costular.atomreminders.data.tasks.TaskAggregated
import com.costular.atomreminders.data.tasks.ReminderEntity
import com.costular.atomreminders.domain.model.*

fun TaskAggregated.toDomain(): Task =
    Task(
        id = task.id,
        name = task.name,
        task.createdAt,
        reminder?.toDomain(),
        task.isDone
    )

fun ReminderEntity.toDomain(): Reminder = Reminder(
    taskId,
    time,
    isEnabled,
    date,
)