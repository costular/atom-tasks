package com.costular.atomreminders.ui.features.habits.create

import com.costular.atomreminders.domain.model.Daily
import com.costular.atomreminders.domain.model.Repetition
import java.time.LocalTime

data class CreateHabitState(
    val name: String = "",
    val repetition: Repetition = Daily,
    val reminderEnabled: Boolean = true,
    val reminderTime: LocalTime = LocalTime.now(),
    val isSaving: Boolean = false,
)