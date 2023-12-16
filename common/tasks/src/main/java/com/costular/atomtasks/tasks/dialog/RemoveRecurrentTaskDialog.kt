package com.costular.atomtasks.tasks.dialog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomtasks.common.tasks.R
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.costular.atomtasks.core.ui.R.string as S

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RemoveRecurrentTaskDialog(
    onCancel: () -> Unit,
    onRemove: (RemoveRecurrentTaskResponse) -> Unit,
) {
    var selected: RemoveRecurrentTaskResponse by remember {
        mutableStateOf(
            RemoveRecurrentTaskResponse.THIS
        )
    }

    BasicAlertDialog(
        onDismissRequest = onCancel,
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation,
        ) {
            Column(modifier = Modifier.padding(vertical = AppTheme.DialogPadding)) {
                Text(
                    text = "Remove recurrence task",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = AppTheme.DialogPadding)
                )

                Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

                SelectableItem(text = stringResource(S.remove_recurring_task_dialog_this),
                    isSelected = selected == RemoveRecurrentTaskResponse.THIS,
                    onClick = {
                        selected = RemoveRecurrentTaskResponse.THIS
                    }
                )

                SelectableItem(text = stringResource(S.remove_recurring_task_dialog_this_and_future),
                    isSelected = selected == RemoveRecurrentTaskResponse.THIS_AND_FUTURES,
                    onClick = {
                        selected = RemoveRecurrentTaskResponse.THIS_AND_FUTURES
                    }
                )

                SelectableItem(text = stringResource(S.remove_recurring_task_dialog_all),
                    isSelected = selected == RemoveRecurrentTaskResponse.ALL,
                    onClick = {
                        selected = RemoveRecurrentTaskResponse.ALL
                    }
                )

                Spacer(Modifier.height(AppTheme.dimens.spacingXLarge))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = AppTheme.DialogPadding),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(onClick = onCancel) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                        )
                    }
                    TextButton(onClick = {
                        onRemove(selected)
                    }) {
                        Text(
                            text = "Remove",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
                        )
                    }
                }
            }
        }
    }

}

@Composable
private fun SelectableItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
    ) {
        RadioButton(
            modifier = Modifier.padding(start = AppTheme.DialogPadding),
            selected = isSelected,
            onClick = onClick
        )

        Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

        Text(
            text = text,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = AppTheme.DialogPadding),
        )
    }
}

enum class RemoveRecurrentTaskResponse {
    THIS, THIS_AND_FUTURES, ALL,
}

@Preview
@Composable
fun RemoveRecurrentTaskDialogPreview() {
    AtomTheme {
        RemoveRecurrentTaskDialog(
            onCancel = {},
            onRemove = {},
        )
    }
}
