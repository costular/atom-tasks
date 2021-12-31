package com.costular.atomreminders.ui.features.agenda

import com.costular.decorit.presentation.base.UiEvent

sealed class AgendaUiEvents : UiEvent {

    object CloseCreateTask : AgendaUiEvents()

}