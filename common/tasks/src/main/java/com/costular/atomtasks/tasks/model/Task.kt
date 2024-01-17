package com.costular.atomtasks.tasks.model

import java.time.LocalDate

data class Task(
    val id: Long,
    val name: String,
    val createdAt: LocalDate,
    val day: LocalDate,
    val reminder: Reminder?,
    val isDone: Boolean,
    val position: Int,
    val isRecurring: Boolean,
    val recurrenceType: RecurrenceType?,
    val recurrenceEndDate: LocalDate?,
    val parentId: Long?,
)
