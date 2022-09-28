package com.costular.atomtasks.settings

import com.costular.atomtasks.data.settings.Theme

data class SettingsState(
    val theme: Theme = Theme.System,
    val moveUndoneTasksTomorrowAutomatically: Boolean = true,
) {
    companion object {
        val Empty = SettingsState()
    }
}
