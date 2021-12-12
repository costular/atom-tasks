package com.costular.atomreminders.ui.features.tasks.create

import com.costular.atomreminders.domain.error.AtomError
import com.costular.decorit.presentation.base.UiEvent

sealed class CreateTaskUiEvent : UiEvent {

    object NavigateUp : CreateTaskUiEvent()

    data class ShowError(val atomError: AtomError) : CreateTaskUiEvent()

}