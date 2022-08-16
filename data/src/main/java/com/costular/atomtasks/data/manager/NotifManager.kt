package com.costular.atomtasks.data.manager

import com.costular.atomtasks.data.tasks.Task

interface NotifManager {
    fun remindTask(task: Task)
    fun removeTaskNotification(taskId: Long)
}
