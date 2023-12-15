package com.costular.atomtasks.tasks.fake

import com.costular.atomtasks.tasks.model.RecurrenceType
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
    isRecurring = false,
    recurrenceEndDate = null,
    recurrenceType = null,
    parentId = null,
)
val TaskWeekly = Task(
    id = 11L,
    name = "Task test",
    createdAt = LocalDate.now(),
    day = LocalDate.now(),
    reminder = null,
    isDone = false,
    position = 0,
    isRecurring = true,
    recurrenceEndDate = null,
    recurrenceType = RecurrenceType.WEEKLY,
    parentId = 1L,
)
