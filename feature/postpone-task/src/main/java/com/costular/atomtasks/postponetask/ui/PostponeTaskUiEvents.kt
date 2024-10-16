package com.costular.atomtasks.postponetask.ui

import com.costular.atomtasks.core.ui.mvi.UiEvent

sealed interface PostponeTaskUiEvents : UiEvent {
    data object PostponedSuccessfully : PostponeTaskUiEvents
}
