package com.atomtasks.feature.detail

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.core.ui.R

@Composable
fun DiscardUnsavedChangesDialog(
    onDismiss: () -> Unit,
    onDiscard: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(
                    R.string.task_detail_discard_changes_title
                )
            )
        },
        text = {
            Text(
                stringResource(
                    R.string.task_detail_discard_changes_description
                )
            )
        },
        confirmButton = {
            Button(onClick = onDiscard) {
                Text(
                    stringResource(
                        R.string.task_detail_discard_changes_positive
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(
                        R.string.task_detail_discard_changes_negative
                    )
                )
            }
        }
    )
}
