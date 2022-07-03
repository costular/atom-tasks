package com.costular.atomtasks.settings

import com.costular.atomtasks.domain.model.Theme

data class SettingsState(
    val theme: Theme = Theme.System,
) {
    companion object {
        val Empty = SettingsState()
    }
}
