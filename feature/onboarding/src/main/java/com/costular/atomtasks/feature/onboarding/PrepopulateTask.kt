package com.costular.atomtasks.feature.onboarding

import com.costular.atomtasks.tasks.model.RecurrenceType
import java.time.LocalDate
import java.time.LocalTime

data class PrepopulateTask(
    val name: String,
    val date: LocalDate,
    val reminder: LocalTime?,
    val recurrence: RecurrenceType?,
    )
