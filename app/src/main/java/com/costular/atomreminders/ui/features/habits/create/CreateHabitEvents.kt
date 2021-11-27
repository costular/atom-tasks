package com.costular.atomreminders.ui.features.habits.create

import com.costular.decorit.presentation.base.UiEvent

sealed class CreateHabitEvents : UiEvent {

    object SavedSuccessfully : CreateHabitEvents()

    data class GoToPage(val page: Int) : CreateHabitEvents()

}
