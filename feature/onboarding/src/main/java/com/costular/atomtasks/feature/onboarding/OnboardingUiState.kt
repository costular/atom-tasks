package com.costular.atomtasks.feature.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.costular.atomtasks.core.ui.R.drawable as D
import com.costular.atomtasks.core.ui.R.string as S

internal data class OnboardingUiState(
    val currentPage: Int = 0,
    val steps: List<OnboardingStep> = OnboardingStep.entries,
) {
    val totalPages: Int get() = steps.size
    val isLastPage: Boolean get() = currentPage == totalPages - 1
}

internal enum class OnboardingStep(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
) {
    TASKS(
        imageRes = D.img_onboarding_tasks,
        titleRes = S.onboarding_tasks_title,
        descriptionRes = S.onboarding_tasks_description,
    ),
    FREE(
        imageRes = D.img_onboarding_free,
        titleRes = S.onboarding_free_title,
        descriptionRes = S.onboarding_free_description,
    ),
    SECURE(
        imageRes = D.img_onboarding_secure,
        titleRes = S.onboarding_secure_title,
        descriptionRes = S.onboarding_secure_description,
    ),
    NOTIFICATIONS(
        imageRes = D.img_onboarding_notification,
        titleRes = S.onboarding_notifications_title,
        descriptionRes = S.onboarding_notifications_description,
    ),
}