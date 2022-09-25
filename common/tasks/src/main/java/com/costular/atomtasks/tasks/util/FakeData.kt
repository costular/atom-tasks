package com.costular.atomtasks.data.util

import com.costular.atomtasks.tasks.Task
import java.time.LocalDate

val taskToday = Task(
    id = 10L,
    name = "Task test",
    createdAt = LocalDate.now(),
    day = LocalDate.now(),
    reminder = null,
    isDone = false,
)
