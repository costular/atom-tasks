package com.costular.atomtasks.settings

import com.costular.atomtasks.data.settings.dailyreminder.DailyReminder
import com.costular.atomtasks.data.settings.Theme
import java.time.LocalTime

data class SettingsState(
    val theme: Theme = Theme.System,
    val moveUndoneTasksTomorrowAutomatically: Boolean = true,
    val dailyReminder: DailyReminder? = DailyReminder(false, LocalTime.of(21, 0)),
    val isDailyReminderTimePickerOpen: Boolean = false,
) {
    companion object {
        val Empty = SettingsState()
    }
}
