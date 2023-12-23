package com.costular.atomtasks.tasks.model

import com.costular.atomtasks.tasks.interactor.UpdateTaskUseCase

sealed interface UpdateTaskUseCaseError {
    data object UnableToSave : UpdateTaskUseCaseError
    data object UnknownError : UpdateTaskUseCaseError
}
