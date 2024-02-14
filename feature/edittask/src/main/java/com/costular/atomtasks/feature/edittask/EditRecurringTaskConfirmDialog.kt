package com.costular.atomtasks.feature.edittask

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
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.costular.atomtasks.core.ui.R.string as S

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecurringTaskConfirmDialog(
    onCancel: () -> Unit,
    onEdit: (EditRecurringTaskResponse) -> Unit,
) {
    var selected: EditRecurringTaskResponse by remember {
        mutableStateOf(
            EditRecurringTaskResponse.THIS
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
                    text = stringResource(S.update_recurring_task_dialog_title),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(horizontal = AppTheme.DialogPadding)
                )

                Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

                SelectableItem(stringResource(S.update_recurring_task_dialog_this),
                    isSelected = selected == EditRecurringTaskResponse.THIS,
                    onClick = {
                        selected = EditRecurringTaskResponse.THIS
                    }
                )

                SelectableItem(stringResource(S.update_recurring_task_dialog_this_and_future),
                    isSelected = selected == EditRecurringTaskResponse.THIS_AND_FUTURE_ONES,
                    onClick = {
                        selected = EditRecurringTaskResponse.THIS_AND_FUTURE_ONES
                    }
                )

                Spacer(Modifier.height(AppTheme.dimens.spacingXLarge))

                DialogButtons(
                    onCancel = onCancel,
                    onEdit = {
                        onEdit(selected)
                    }
                )
            }
        }
    }

}

@Composable
private fun DialogButtons(
    onCancel: () -> Unit,
    onEdit: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = AppTheme.DialogPadding),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(onClick = onCancel) {
            Text(
                text = stringResource(S.cancel),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            )
        }
        TextButton(onClick = onEdit) {
            Text(
                text = stringResource(S.accept),
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            )
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

enum class EditRecurringTaskResponse {
    THIS,
    THIS_AND_FUTURE_ONES,
}

@Preview
@Composable
private fun EditRecurringTaskConfirmDialogPreview() {
    AtomTheme {
        EditRecurringTaskConfirmDialog(
            onCancel = {},
            onEdit = {},
        )
    }
}
