package com.atomtasks.feature.detail

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.core.ui.R

@Composable
fun NotificationRationale(
    onDismiss: () -> Unit,
    onAccept: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(
                    R.string.create_task_notification_permission_rationale_title
                )
            )
        },
        text = {
            Text(
                stringResource(
                    R.string.create_task_notification_permission_rationale_description
                )
            )
        },
        confirmButton = {
            TextButton(onClick = onAccept) {
                Text(
                    stringResource(
                        R.string.create_task_notification_permission_rationale_confirm
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(
                        R.string.create_task_notification_permission_rationale_dismiss
                    )
                )
            }
        }
    )
}
