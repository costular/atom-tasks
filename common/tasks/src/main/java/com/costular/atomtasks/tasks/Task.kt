package com.costular.atomtasks.tasks

import java.time.LocalDate

data class Task(
    val id: Long,
    val name: String,
    val createdAt: LocalDate,
    val day: LocalDate,
    val reminder: Reminder?,
    val isDone: Boolean,
)
