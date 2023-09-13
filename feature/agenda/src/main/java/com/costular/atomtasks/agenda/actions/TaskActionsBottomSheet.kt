package com.costular.atomtasks.agenda.actions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.components.ActionItem
import com.costular.designsystem.components.Draggable
import com.costular.designsystem.theme.AlphaDivider
import com.costular.designsystem.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun ColumnScope.TasksActionsBottomSheet(
    result: ResultBackNavigator<TaskActionsResult>,
    taskId: Long,
    taskName: String,
    isDone: Boolean,
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.navigationBarsPadding(),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Draggable(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = AppTheme.dimens.spacingMedium)
            )

            Spacer(Modifier.height(AppTheme.dimens.spacingXLarge))

            Text(
                text = taskName ?: "",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin)
                    .testTag("TaskActionTitle"),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimens.spacingLarge)
                    .padding(bottom = AppTheme.dimens.spacingSmall),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = AlphaDivider),
            )

            if (!isDone) {
                ActionItem(
                    icon = Icons.Outlined.Done,
                    text = stringResource(R.string.agenda_mark_as_done),
                    onClick = { result.navigateBack(TaskActionsResult.MarkAsDone(taskId)) },
                    modifier = Modifier.testTag("TaskActionDone"),
                )
            } else {
                ActionItem(
                    icon = Icons.Outlined.Close,
                    text = stringResource(R.string.agenda_mark_as_undone),
                    onClick = { result.navigateBack(TaskActionsResult.MarkAsNotDone(taskId)) },
                    modifier = Modifier.testTag("TaskActionUndone"),
                )
            }

            ActionItem(
                icon = Icons.Outlined.Edit,
                text = stringResource(R.string.agenda_edit_task),
                onClick = { result.navigateBack(TaskActionsResult.Edit(taskId)) },
                modifier = Modifier.testTag("TaskActionEdit"),
            )

            ActionItem(
                icon = Icons.Outlined.Delete,
                text = stringResource(R.string.agenta_delete_task),
                onClick = { result.navigateBack(TaskActionsResult.Remove(taskId)) },
                modifier = Modifier.testTag("TaskActionDelete"),
            )
        }
    }
}

@Preview
@Composable
fun TaskActionsPreview() {
    Column {
        TasksActionsBottomSheet(
            EmptyResultBackNavigator(),
            taskId = 1L,
            taskName = "\uD83C\uDFC3\uD83C\uDFFC\u200D♂️ Go out to run",
            isDone = false,
        )
    }
}
