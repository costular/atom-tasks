package com.costular.atomtasks.tasks.helper

import java.time.LocalDateTime

interface TaskReminderManager {
    fun canScheduleReminders(): Boolean
    fun set(taskId: Long, localDateTime: LocalDateTime)
    fun cancel(taskId: Long)
}
