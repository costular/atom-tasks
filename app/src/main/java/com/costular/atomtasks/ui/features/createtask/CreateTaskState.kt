package com.costular.atomtasks.ui.features.createtask

import com.costular.atomtasks.domain.Async

data class CreateTaskState(
    val savingTask: Async<Unit> = Async.Uninitialized,
) {

    companion object {
        val Empty = CreateTaskState()
    }
}
