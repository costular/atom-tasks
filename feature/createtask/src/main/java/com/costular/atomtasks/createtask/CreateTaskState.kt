package com.costular.atomtasks.createtask

sealed interface CreateTaskState {

    data object Uninitialized : CreateTaskState
    data object Loading : CreateTaskState
    data object Saving : CreateTaskState
    data object Success : CreateTaskState
    data object Failure : CreateTaskState

}
