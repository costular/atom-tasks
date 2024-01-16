package com.costular.atomtasks.tasks.model

sealed interface RemoveTaskError {
    data object UnknownError : RemoveTaskError
}
