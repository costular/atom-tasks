package com.costular.commonui.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.window.Dialog
import com.costular.common_ui.R
import com.costular.commonui.components.ActionItem
import com.costular.commonui.theme.AlphaDivider
import com.costular.commonui.theme.AppTheme

@Composable
fun TaskActionDialog(
    taskName: String?,
    isDone: Boolean,
    onDelete: () -> Unit,
    onDone: () -> Unit,
    onUndone: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppTheme.dimens.contentMargin),
            ) {
                Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

                Text(
                    text = taskName ?: "",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = AppTheme.dimens.contentMargin)
                        .testTag("TaskActionTitle"),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = AppTheme.dimens.spacingLarge)
                        .padding(bottom = AppTheme.dimens.spacingSmall),
                    color = MaterialTheme.colors.onSurface.copy(alpha = AlphaDivider),
                )

                if (!isDone) {
                    ActionItem(
                        icon = Icons.Outlined.Done,
                        text = stringResource(R.string.agenda_mark_as_done),
                        onClick = onDone,
                        modifier = Modifier.testTag("TaskActionDone"),
                    )
                } else {
                    ActionItem(
                        icon = Icons.Outlined.Close,
                        text = stringResource(R.string.agenda_mark_as_undone),
                        onClick = onUndone,
                        modifier = Modifier.testTag("TaskActionUndone"),
                    )
                }

                ActionItem(
                    icon = Icons.Outlined.Delete,
                    text = stringResource(R.string.agenta_delete_task),
                    onClick = onDelete,
                    modifier = Modifier.testTag("TaskActionDelete"),
                )
            }
        }
    }
}
