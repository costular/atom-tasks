package com.costular.atomreminders.ui.features.habits.list

import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Habit
import java.time.LocalDate

data class HabitListState(
    val selectedDay: LocalDate = LocalDate.now(),
    val habits: Async<List<Habit>> = Async.Uninitialized
)