package com.costular.atomhabits.ui.features.habits.create

import com.costular.atomhabits.domain.model.Daily
import com.costular.atomhabits.domain.model.Reminder
import com.costular.atomhabits.domain.model.Repetition
import java.time.LocalTime

data class CreateHabitState(
    val name: String = "",
    val repetition: Repetition = Daily,
    val reminderEnabled: Boolean = true,
    val reminderTime: LocalTime = LocalTime.now(),
    val isSaving: Boolean = false,
)