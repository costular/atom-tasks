package com.costular.atomtasks.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FastForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.theme.AtomTheme

@Composable
fun TasksSettingsSection(
    isMoveUndoneTasksTomorrowEnabled: Boolean,
    onSetMoveUndoneTasksTomorrow: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingSection(
        title = stringResource(R.string.settings_tasks_title),
        modifier = modifier.fillMaxWidth(),
    ) {
        SettingSwitch(
            start = {
                Icon(imageVector = Icons.Outlined.FastForward, contentDescription = null)
            },
            title = {
                Column {
                    Text(
                        text = stringResource(R.string.settings_tasks_autoforward_tasks_title),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.settings_tasks_autoforward_tasks_description,
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            isSelected = isMoveUndoneTasksTomorrowEnabled,
            onSelect = onSetMoveUndoneTasksTomorrow,
            modifier = modifier,
        )
    }
}

@Preview
@Composable
fun TasksSettingsSection() {
    AtomTheme {
        TasksSettingsSection(
            isMoveUndoneTasksTomorrowEnabled = true,
            onSetMoveUndoneTasksTomorrow = {},
        )
    }
}
