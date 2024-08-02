package com.atomtasks.feature.detail

import com.costular.atomtasks.core.ui.mvi.UiEvent

sealed interface TaskDetailUiEvent : UiEvent {
    data object Close : TaskDetailUiEvent
    data object NavigateToExactAlarmSettings : TaskDetailUiEvent
}