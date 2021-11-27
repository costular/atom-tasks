package com.costular.atomreminders.domain.model

import java.time.LocalDate

data class HabitRecord(
    val id: Long,
    val date: LocalDate
)