package com.costular.commonui.components.createtask

import java.time.LocalDate
import java.time.LocalTime

data class CreateTaskResult(
    val name: String,
    val date: LocalDate,
    val reminder: LocalTime?,
)
