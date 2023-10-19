package com.costular.atomtasks.notifications

interface TaskNotificationManager {
    fun remindTask(taskId: Long, taskName: String)
    fun removeTaskNotification(taskId: Long)
}
