package com.costular.atomtasks.tasks.manager

import com.costular.atomtasks.tasks.Task

interface TaskNotificationManager {
    fun remindTask(task: Task)
    fun removeTaskNotification(taskId: Long)
}
