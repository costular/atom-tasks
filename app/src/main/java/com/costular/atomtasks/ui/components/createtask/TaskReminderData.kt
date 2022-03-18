package com.costular.atomtasks.ui.components.createtask

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.components.TimePicker
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import java.time.LocalTime

@Composable
internal fun TaskReminderData(
    reminder: LocalTime,
    onSelectReminder: (LocalTime?) -> Unit,
    viewModel: TaskReminderDataViewModel = viewModel(),
) {
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(TaskReminderDataState.Empty)

    TaskReminderData(
        isEnabled = state.isEnabled,
        time = reminder,
        onToggleEnable = viewModel::toggleEnabled,
        onEnableReminder = viewModel::enableReminder,
        onUpdateReminder = onSelectReminder,
    )
}

@Composable
private fun TaskReminderData(
    isEnabled: Boolean,
    time: LocalTime,
    onToggleEnable: () -> Unit,
    onEnableReminder: (Boolean) -> Unit,
    onUpdateReminder: (LocalTime?) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppTheme.dimens.spacingMedium)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .selectable(
                    isEnabled,
                    role = Role.Switch,
                    onClick = onToggleEnable
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                stringResource(R.string.enable_reminder),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.subtitle1,
            )

            Switch(
                checked = isEnabled,
                onCheckedChange = { isEnabled ->
                    onEnableReminder(isEnabled)
                    onUpdateReminder(null)
                },
            )
        }

        AnimatedVisibility(isEnabled) {
            TimePicker(
                modifier = Modifier.fillMaxWidth(),
                time = time,
                onTimeChange = { time ->
                    onUpdateReminder(time)
                },
            )
        }
    }
}
