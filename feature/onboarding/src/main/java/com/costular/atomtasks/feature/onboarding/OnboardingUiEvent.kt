package com.costular.atomtasks.feature.onboarding

import com.costular.atomtasks.core.ui.mvi.UiEvent

sealed interface OnboardingUiEvent : UiEvent {
    data object RequestNotificationPermission : OnboardingUiEvent
    data object NavigateToAgenda : OnboardingUiEvent
}
