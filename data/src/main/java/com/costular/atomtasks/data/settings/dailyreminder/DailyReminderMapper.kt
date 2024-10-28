package com.costular.atomtasks.data.settings.dailyreminder

import java.time.LocalTime

fun DailyReminderDto.asDomain(): DailyReminder = DailyReminder(
    isEnabled = isEnabled,
    time = time?.let(LocalTime::parse),
)

fun DailyReminder.asDto(): DailyReminderDto = DailyReminderDto(
    isEnabled = isEnabled,
    time = time?.toString(),
)
