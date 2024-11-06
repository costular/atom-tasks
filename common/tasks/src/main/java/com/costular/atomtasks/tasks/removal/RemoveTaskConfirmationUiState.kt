package com.costular.atomtasks.tasks.removal

sealed interface RemoveTaskConfirmationUiState {
    data object Hidden : RemoveTaskConfirmationUiState

    data class Shown(
        val taskId: Long,
        val isRecurring: Boolean,
    ) : RemoveTaskConfirmationUiState
}
