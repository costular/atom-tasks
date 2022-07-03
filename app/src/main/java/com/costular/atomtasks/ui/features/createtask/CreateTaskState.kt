package com.costular.atomtasks.ui.features.createtask

data class CreateTaskState(
    val savingTask: Async<Unit> = Async.Uninitialized,
) {

    companion object {
        val Empty = CreateTaskState()
    }
}
