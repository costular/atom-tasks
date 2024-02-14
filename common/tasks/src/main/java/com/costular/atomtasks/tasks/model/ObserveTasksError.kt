package com.costular.atomtasks.tasks.model

sealed interface ObserveTasksError {
    data object UnknownError : ObserveTasksError
}
