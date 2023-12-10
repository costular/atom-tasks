package com.costular.atomtasks.core.ui.date

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class Day(
    val date: LocalDate,
)

fun LocalDate.asDay(): Day = Day(this)
