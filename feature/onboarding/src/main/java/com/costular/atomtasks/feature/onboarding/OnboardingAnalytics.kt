package com.costular.atomtasks.feature.onboarding

import com.costular.atomtasks.analytics.TrackingEvent

internal object OnboardingAnalytics {
    data object Skipped : TrackingEvent(name = "onboarding_skipped")
    data object Next : TrackingEvent(name = "onboarding_next")
    data object Finished : TrackingEvent(name = "onboarding_finished")
    data class PermissionAnswered(val granted: Boolean) : TrackingEvent(
        name = "onboarding_notification_permission_answered",
        mapOf("granted" to granted)
    )
}
