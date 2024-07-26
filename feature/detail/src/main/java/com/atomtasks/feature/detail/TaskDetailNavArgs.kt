package com.atomtasks.feature.detail

import java.time.LocalDate

data class TaskDetailNavArgs(
    val defaultDate: LocalDate? = null,
    val taskId: Long? = null,
)