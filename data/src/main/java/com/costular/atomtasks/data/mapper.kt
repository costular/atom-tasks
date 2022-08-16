package com.costular.atomtasks.data

import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.Reminder
import com.costular.atomtasks.data.tasks.Task

fun TaskAggregated.toDomain(): Task =
    Task(
        id = task.id,
        name = task.name,
        task.createdAt,
        reminder?.toDomain(),
        task.isDone,
    )

fun ReminderEntity.toDomain(): Reminder = Reminder(
    taskId,
    time,
    isEnabled,
    date,
)
