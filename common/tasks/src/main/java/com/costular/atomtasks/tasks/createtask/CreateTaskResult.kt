package com.costular.atomtasks.tasks.createtask

import com.costular.atomtasks.tasks.model.RecurrenceType
import java.time.LocalDate
import java.time.LocalTime

data class CreateTaskResult(
    val name: String,
    val date: LocalDate,
    val reminder: LocalTime?,
    val recurrenceType: RecurrenceType?,
)
