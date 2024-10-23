package com.costular.atomtasks.data.settings.dailyreminder

import java.time.LocalTime

data class DailyReminder(
    val isEnabled: Boolean,
    val time: LocalTime?,
)