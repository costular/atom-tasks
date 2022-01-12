package com.costular.atomtasks.domain.manager

import com.costular.atomtasks.domain.model.Task

interface NotifManager {

    fun remindTask(task: Task)
    fun removeTaskNotification(taskId: Long)

}