package com.costular.atomhabits.ui.features.habits.create

import com.costular.atomhabits.domain.model.Daily
import com.costular.atomhabits.domain.model.Repetition

data class CreateHabitState(
    val name: String = "",
    val repetition: Repetition = Daily,
    val isSaving: Boolean = false,
)