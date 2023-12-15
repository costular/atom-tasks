package com.costular.atomtasks.tasks.model

sealed interface UpdateTaskIsDoneError {
    data object UnknownError : UpdateTaskIsDoneError
}
