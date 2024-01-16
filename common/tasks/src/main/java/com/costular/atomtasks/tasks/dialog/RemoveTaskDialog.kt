<<<<<<<< HEAD:common/tasks/src/main/java/com/costular/atomtasks/tasks/dialog/RemoveTaskDialog.kt
package com.costular.atomtasks.tasks.dialog
========
package com.costular.designsystem.dialogs
>>>>>>>> main:core/designsystem/src/main/java/com/costular/designsystem/dialogs/RemoveTaskDialog.kt

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.core.ui.R

@Composable
fun RemoveTaskDialog(
    onAccept: () -> Unit,
    onCancel: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onCancel,
        text = {
            Text(stringResource(R.string.remove_task_message))
        },
        confirmButton = {
            TextButton(onClick = onAccept) {
                Text(stringResource(R.string.delete))
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text(stringResource(R.string.cancel))
            }
        },
    )
}
