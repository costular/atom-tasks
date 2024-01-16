package com.costular.atomtasks.tasks.model

sealed interface UpdateTaskUseCaseError {
    data object UnableToSave : UpdateTaskUseCaseError
    data object UnknownError : UpdateTaskUseCaseError
}
