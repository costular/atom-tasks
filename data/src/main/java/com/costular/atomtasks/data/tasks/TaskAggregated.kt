package com.costular.atomtasks.data.tasks

import androidx.room.Embedded
import androidx.room.Relation

data class TaskAggregated(
    @Embedded val task: TaskEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "task_id",
    )
    val reminder: ReminderEntity?,
)
