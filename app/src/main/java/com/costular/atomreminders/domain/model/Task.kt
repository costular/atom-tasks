package com.costular.atomreminders.domain.model

import java.time.LocalDate

data class Task(
    val id: Long,
    val name: String,
    val createdAt: LocalDate,
    val reminder: Reminder?,
    val isDone: Boolean
)