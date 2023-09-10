package com.costular.atomtasks.coreui.date

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class Day(
    val date: LocalDate,
)

fun LocalDate.asDay(): Day = Day(this)
