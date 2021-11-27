package com.costular.atomreminders.ui.features.habits.detail

import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Habit

data class HabitDetailState(
    val habit: Async<Habit> = Async.Uninitialized
)