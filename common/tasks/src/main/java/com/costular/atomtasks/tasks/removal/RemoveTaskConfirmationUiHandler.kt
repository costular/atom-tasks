package com.costular.atomtasks.tasks.removal

import androidx.compose.runtime.Composable
import com.costular.atomtasks.tasks.removal.RemoveRecurrentTaskResponse.ALL
import com.costular.atomtasks.tasks.removal.RemoveRecurrentTaskResponse.THIS
import com.costular.atomtasks.tasks.removal.RemoveRecurrentTaskResponse.THIS_AND_FUTURES

@Composable
fun RemoveTaskConfirmationUiHandler(
    uiState: RemoveTaskConfirmationUiState,
    onDismiss: () -> Unit,
    onDeleteRecurring: (taskId: Long, recurringRemovalStrategy: RecurringRemovalStrategy) -> Unit,
    onDelete: (taskId: Long) -> Unit,
) {
    when (uiState) {
        RemoveTaskConfirmationUiState.Hidden -> Unit

        is RemoveTaskConfirmationUiState.Shown -> RemovalDialog(
            taskId = uiState.taskId,
            isRecurring = uiState.isRecurring,
            onDismiss = onDismiss,
            onDeleteRecurring = onDeleteRecurring,
            onDelete = onDelete
        )
    }
}

@Composable
private fun RemovalDialog(
    taskId: Long,
    isRecurring: Boolean,
    onDismiss: () -> Unit,
    onDeleteRecurring: (taskId: Long, recurringRemovalStrategy: RecurringRemovalStrategy) -> Unit,
    onDelete: (taskId: Long) -> Unit,
) {
    if (isRecurring) {
        RemoveRecurrentTaskDialog(
            onCancel = onDismiss,
            onRemove = { response ->
                val recurringRemovalStrategy = when (response) {
                    THIS -> RecurringRemovalStrategy.SINGLE
                    THIS_AND_FUTURES -> RecurringRemovalStrategy.SINGLE_AND_FUTURE_ONES
                    ALL -> RecurringRemovalStrategy.ALL
                }
                onDeleteRecurring(taskId, recurringRemovalStrategy)
            }
        )
    } else {
        RemoveTaskDialog(
            onAccept = {
                onDelete(taskId)
            },
            onCancel = onDismiss,
        )
    }
}
