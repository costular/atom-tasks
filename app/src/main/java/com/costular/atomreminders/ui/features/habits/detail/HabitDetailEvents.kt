package com.costular.atomreminders.ui.features.habits.detail

import com.costular.decorit.presentation.base.UiEvent

sealed class HabitDetailEvents : UiEvent {

    object GoBack : HabitDetailEvents()

}