package com.costular.atomtasks.data.settings.dailyreminder

import kotlinx.serialization.Serializable

@Serializable
data class DailyReminderDto(
    val isEnabled: Boolean,
    val time: String?,
)
