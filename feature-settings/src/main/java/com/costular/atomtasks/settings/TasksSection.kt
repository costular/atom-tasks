package com.costular.atomtasks.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.costular.commonui.theme.AppTheme
import com.costular.commonui.theme.AtomRemindersTheme

@Composable
fun TasksSection(
    moveUndoneTasksToTomorrowAutomatically: Boolean,
    onChangeUndoneTasks: (Boolean) -> Unit,
) {
    SettingSection(
        stringResource(R.string.settings_tasks_section_title),
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingSwitch(
            title = {
                Column {
                    Text(
                        stringResource(R.string.settings_move_undone_tasks_title),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.height(AppTheme.dimens.spacingMedium))
                    Text(
                        stringResource(R.string.settings_move_undone_tasks_description),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            isSelected = moveUndoneTasksToTomorrowAutomatically,
            onSelect = onChangeUndoneTasks,
        )
    }
}

@Preview
@Composable
fun TasksSectionPreview() {
    AtomRemindersTheme {
        TasksSection(
            moveUndoneTasksToTomorrowAutomatically = true,
            onChangeUndoneTasks = {},
        )
    }
}
