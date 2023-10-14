package com.costular.atomtasks.data.util

import com.costular.atomtasks.tasks.model.Task
import java.time.LocalDate

val TaskToday = Task(
    id = 10L,
    name = "Task test",
    createdAt = LocalDate.now(),
    day = LocalDate.now(),
    reminder = null,
    isDone = false,
    position = 0,
)
