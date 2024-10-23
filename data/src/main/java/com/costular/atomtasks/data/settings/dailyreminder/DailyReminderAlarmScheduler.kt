package com.costular.atomtasks.data.settings.dailyreminder

import java.time.LocalDateTime

interface DailyReminderAlarmScheduler {
    fun schedule(localDateTime: LocalDateTime)
    fun remove()
}