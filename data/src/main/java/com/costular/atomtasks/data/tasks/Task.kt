package com.costular.atomtasks.data.tasks

import java.time.LocalDate

data class Task(
    val id: Long,
    val name: String,
    val createdAt: LocalDate,
    val reminder: Reminder?,
    val isDone: Boolean,
)
