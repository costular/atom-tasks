package com.costular.atomtasks.feature.onboarding

import androidx.lifecycle.viewModelScope
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.ui.mvi.MviViewModel
import com.costular.atomtasks.core.usecase.EmptyParams
import com.costular.atomtasks.data.tutorial.OnboardingShownUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val atomAnalytics: AtomAnalytics,
    private val prepopulateOnboardingTasksUseCase: PrepopulateOnboardingTasksUseCase,
    private val onboardingShownUseCase: OnboardingShownUseCase,
) : MviViewModel<OnboardingUiState>(OnboardingUiState()) {

    init {
        setOnboardingShown()
        prepopulateDatabase()
    }

    private fun setOnboardingShown() {
        viewModelScope.launch {
            onboardingShownUseCase.invoke(EmptyParams)
        }
    }

    private fun prepopulateDatabase() {
        viewModelScope.launch {
            prepopulateOnboardingTasksUseCase.invoke(EmptyParams)
        }
    }

    fun onPageChanged(newPage: Int) {
        viewModelScope.launch {
            setState { copy(currentPage = newPage) }
        }
    }

    fun onNext() {
        atomAnalytics.track(OnboardingAnalytics.Next)
    }

    fun onSkip() {
        atomAnalytics.track(OnboardingAnalytics.Skipped)
        navigateToAgenda()
    }

    fun onFinish(shouldRequestPermission: Boolean) {
        atomAnalytics.track(OnboardingAnalytics.Finished)

        if (shouldRequestPermission) {
            requestPermission()
        } else {
            navigateToAgenda()
        }
    }

    private fun requestPermission() {
        sendEvent(OnboardingUiEvent.RequestNotificationPermission)
    }

    fun onPermission(granted: Boolean) {
        atomAnalytics.track(OnboardingAnalytics.PermissionAnswered(granted))
        navigateToAgenda()
    }

    private fun navigateToAgenda() {
        sendEvent(OnboardingUiEvent.NavigateToAgenda)
    }
}