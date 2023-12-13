package com.costular.atomtasks.tasks.model

sealed interface CreateTaskError {
    data object UnknownError : CreateTaskError
}
