package com.costular.atomtasks.tasks.fake

import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.Reminder
import com.costular.atomtasks.tasks.model.Task
import java.time.LocalDate
import java.time.LocalTime

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
val TaskRecurring = Task(
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
val TaskWithReminder = Task(
    id = 11L,
    name = "Task test",
    createdAt = LocalDate.now(),
    day = LocalDate.now(),
    reminder = Reminder(
        id = 1L,
        time = LocalTime.of(9, 0),
        date = LocalDate.now(),
    ),
    isDone = false,
    position = 0,
    isRecurring = false,
    recurrenceEndDate = null,
    recurrenceType = RecurrenceType.WEEKLY,
    parentId = 1L,
)
val TaskRecurringWithReminder = Task(
    id = 11L,
    name = "Task test",
    createdAt = LocalDate.now(),
    day = LocalDate.now(),
    reminder = Reminder(
        id = 1L,
        time = LocalTime.of(9, 0),
        date = LocalDate.now(),
    ),
    isDone = false,
    position = 0,
    isRecurring = true,
    recurrenceEndDate = null,
    recurrenceType = RecurrenceType.WEEKLY,
    parentId = 1L,
)
val TaskFinished = Task(
    id = 11L,
    name = "Task test",
    createdAt = LocalDate.now(),
    day = LocalDate.now(),
    reminder = null,
    isDone = true,
    position = 0,
    isRecurring = false,
    recurrenceEndDate = null,
    recurrenceType = RecurrenceType.WEEKLY,
    parentId = 1L,
)
