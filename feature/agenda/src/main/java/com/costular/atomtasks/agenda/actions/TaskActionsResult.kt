package com.costular.atomtasks.agenda.actions

import java.io.Serializable

sealed class TaskActionsResult(open val taskId: Long) : Serializable {
    data class MarkAsDone(override val taskId: Long) : TaskActionsResult(taskId)

    data class MarkAsNotDone(override val taskId: Long) : TaskActionsResult(taskId)

    data class Edit(override val taskId: Long) : TaskActionsResult(taskId)

    data class Remove(override val taskId: Long) : TaskActionsResult(taskId)
}
