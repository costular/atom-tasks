package com.costular.atomhabits.ui.features.habits.list

import com.costular.atomhabits.domain.Async
import com.costular.atomhabits.domain.model.Habit
import java.time.LocalDate

data class HabitListState(
    val selectedDay: LocalDate = LocalDate.now(),
    val habits: Async<List<Habit>> = Async.Uninitialized
)