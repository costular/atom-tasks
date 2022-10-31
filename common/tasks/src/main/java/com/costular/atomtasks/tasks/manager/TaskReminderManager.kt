package com.costular.atomtasks.tasks.manager

import java.time.LocalDateTime

interface TaskReminderManager {
    fun set(taskId: Long, localDateTime: LocalDateTime)
    fun cancel(taskId: Long)
}
