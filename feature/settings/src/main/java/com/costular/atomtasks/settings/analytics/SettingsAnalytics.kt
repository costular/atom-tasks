package com.costular.atomtasks.settings.analytics

import com.costular.atomtasks.analytics.TrackingEvent

data class SettingsChangeTheme(val theme: String) : TrackingEvent(
    name = "settings_change_theme",
    attributes = mapOf("theme" to theme),
)

data class SettingsChangeAutoforward(val isEnabled: Boolean) : TrackingEvent(
    name = "settings_change_autoforward",
    attributes = mapOf("enabled" to isEnabled),
)
