package com.costular.atomtasks.createtask

import com.costular.core.Async

data class CreateTaskState(
    val savingTask: Async<Unit> = Async.Uninitialized,
) {

    companion object {
        val Empty = CreateTaskState()
    }
}
