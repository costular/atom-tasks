package com.costular.atomhabits.ui.features.habits.detail

import com.costular.atomhabits.domain.Async
import com.costular.atomhabits.domain.model.Habit

data class HabitDetailState(
    val habit: Async<Habit> = Async.Uninitialized
)