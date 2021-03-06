package com.costular.atomtasks.domain.manager

import java.time.LocalDateTime

interface ReminderManager {

    fun set(taskId: Long, localDateTime: LocalDateTime)
    fun cancel(taskId: Long)
}
