package com.costular.atomtasks.tasks.createtask

import android.app.AlarmManager.ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.core.ui.R
import com.costular.atomtasks.core.ui.utils.DateUtils.dayAsText
import com.costular.atomtasks.core.ui.utils.ofLocalizedTime
import com.costular.atomtasks.tasks.format.localized
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.designsystem.components.ClearableChip
import com.costular.designsystem.components.PrimaryButton
import com.costular.designsystem.dialogs.DatePickerDialog
import com.costular.designsystem.dialogs.TimePickerDialog
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.time.LocalDate
import java.time.LocalTime

@ExperimentalMaterial3Api
@Suppress("MagicNumber", "LongMethod")
@Composable
fun CreateTaskExpanded(
    value: String,
    date: LocalDate,
    onSave: (CreateTaskResult) -> Unit,
    modifier: Modifier = Modifier,
    reminder: LocalTime? = null,
) {
    val context = LocalContext.current

    val viewModel: CreateTaskExpandedViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        viewModel.init()
    }

    LaunchedEffect(value) {
        viewModel.setName(value)
    }

    LaunchedEffect(date) {
        viewModel.setDate(date)
    }

    LaunchedEffect(reminder) {
        viewModel.setReminder(reminder)
    }

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is CreateTaskUiEvents.SaveTask -> {
                    onSave(event.taskResult)
                }

                is CreateTaskUiEvents.NavigateToExactAlarmSettings -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                    }
                }
            }
        }
    }

    if (state.showSetDate) {
        DatePickerDialog(
            onDismiss = viewModel::closeSelectDate,
            currentDate = state.date,
            onDateSelected = viewModel::setDate,
        )
    }

    if (state.showSetReminder) {
        if (state.shouldShowAlarmsRationale) {
            ExactAlarmRationale(
                onDismiss = viewModel::closeSelectReminder,
                navigateToExactAlarmSettings = viewModel::navigateToExactAlarmSettings,
                onPermissionStateChanged = viewModel::exactAlarmSettingChanged
            )
        } else {
            NotificationPermissionEffect(
                onRevoke = viewModel::closeSelectReminder
            ) {
                TimePickerDialog(
                    onDismiss = viewModel::closeSelectReminder,
                    selectedTime = state.reminder,
                    onSelectTime = viewModel::setReminder,
                )
            }
        }
    }

    if (state.showSetRecurrence) {
        RecurrenceTypePickerDialog(
            recurrenceType = state.recurrenceType,
            onRecurrenceTypeSelected = viewModel::setRecurrence,
            onDismissRequest = viewModel::closeSelectRecurrence,
        )
    }

    CreateTaskExpanded(
        state = state,
        modifier = modifier,
        focusRequester = focusRequester,
        onValueChange = viewModel::setName,
        onClickDate = viewModel::selectDate,
        onClickReminder = viewModel::selectReminder,
        onClearReminder = viewModel::clearReminder,
        onSave = viewModel::requestSave,
        onClickRecurrence = viewModel::selectRecurrence,
        onClearRecurrence = viewModel::clearRecurrence,
    )
}

@Composable
private fun ExactAlarmRationale(
    onDismiss: () -> Unit,
    navigateToExactAlarmSettings: () -> Unit,
    onPermissionStateChanged: () -> Unit,
) {
    ObserveScheduleExactAlarmPermissionState(onPermissionStateChanged)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                stringResource(
                    R.string.create_task_schedule_exact_reminder_permission_missing_title
                )
            )
        },
        text = {
            Text(
                stringResource(
                    R.string.create_task_schedule_exact_reminder_permission_missing_description
                )
            )
        },
        confirmButton = {
            TextButton(onClick = navigateToExactAlarmSettings) {
                Text(
                    stringResource(
                        R.string.create_task_schedule_exact_reminder_permission_missing_confirm
                    )
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    stringResource(
                        R.string.create_task_schedule_exact_reminder_permission_missing_dismiss
                    )
                )
            }
        }
    )
}

@Composable
private fun ObserveScheduleExactAlarmPermissionState(onPermissionStateChanged: () -> Unit) {
    val context = LocalContext.current

    DisposableEffect(context) {
        val intentFilter = IntentFilter(ACTION_SCHEDULE_EXACT_ALARM_PERMISSION_STATE_CHANGED)
        val broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                onPermissionStateChanged()
            }
        }
        context.registerReceiver(broadcastReceiver, intentFilter)

        onDispose {
            context.unregisterReceiver(broadcastReceiver)
        }
    }
}

@Composable
internal fun CreateTaskExpanded(
    state: CreateTaskExpandedState,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    onClickDate: () -> Unit,
    onClickReminder: () -> Unit,
    onClearReminder: () -> Unit,
    onClickRecurrence: () -> Unit,
    onClearRecurrence: () -> Unit,
    onSave: () -> Unit,
) {
    Column(modifier = modifier) {
        CreateTaskInput(
            modifier = Modifier.padding(
                top = AppTheme.dimens.contentMargin,
                start = AppTheme.dimens.contentMargin,
                end = AppTheme.dimens.contentMargin,
            ),
            value = state.name,
            focusRequester = focusRequester,
            onValueChange = onValueChange,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingSmall))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = AppTheme.dimens.contentMargin),
        ) {
            DueDateButton(
                state.date,
                onClickDate,
                onClearReminder,
            )

            Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

            ReminderButton(
                state.reminder,
                state.isReminderError,
                onClickReminder,
                onClearReminder
            )

            Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

            RecurrenceButton(
                recurrenceType = state.recurrenceType,
                onClick = onClickRecurrence,
                onClearRecurrence = onClearRecurrence,
            )
        }

        if (state.isReminderError) {
            Text(
                text = stringResource(R.string.create_task_reminder_past_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.contentMargin),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium,
            )
        }

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        SaveButton(
            isEnabled = state.shouldShowSend,
            onSave = onSave,
            modifier = Modifier.fillMaxWidth().padding(
                start = AppTheme.dimens.contentMargin,
                end = AppTheme.dimens.contentMargin,
                bottom = AppTheme.dimens.spacingMedium,
            ),
        )
    }
}

@Composable
private fun DueDateButton(
    date: LocalDate,
    onClickDate: () -> Unit,
    onClearReminder: () -> Unit
) {
    ClearableChip(
        title = dayAsText(date),
        icon = Icons.Outlined.Today,
        isSelected = false,
        onClick = onClickDate,
        onClear = onClearReminder,
        isError = false,
        modifier = Modifier.testTag("CreateTaskExpandedDate"),
    )
}

@Composable
private fun ReminderButton(
    reminder: LocalTime?,
    isError: Boolean,
    onClickReminder: () -> Unit,
    onClearReminder: () -> Unit
) {
    val reminderText = if (reminder != null) {
        reminder.ofLocalizedTime()
    } else {
        stringResource(R.string.create_task_set_reminder)
    }

    ClearableChip(
        title = reminderText,
        icon = Icons.Outlined.Alarm,
        isSelected = reminder != null,
        onClick = onClickReminder,
        onClear = onClearReminder,
        isError = isError,
        modifier = Modifier.testTag("CreateTaskExpandedReminder"),
    )
}

@Composable
private fun RecurrenceButton(
    recurrenceType: RecurrenceType?,
    onClick: () -> Unit,
    onClearRecurrence: () -> Unit,
) {
    val buttonText = if (recurrenceType != null) {
        recurrenceType.localized()
    } else {
        stringResource(R.string.create_task_set_recurrence)
    }

    ClearableChip(
        title = buttonText,
        icon = Icons.Outlined.Repeat,
        isSelected = recurrenceType != null,
        isError = false,
        onClick = onClick,
        onClear = onClearRecurrence,
    )
}

@Composable
private fun CreateTaskInput(
    value: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                stringResource(R.string.agenda_create_task_name),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .testTag("CreateTaskInput")
            .focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.bodyLarge,
        maxLines = 5,
    )
}

@Composable
fun SaveButton(
    isEnabled: Boolean,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PrimaryButton(
        onClick = onSave,
        modifier = modifier.testTag("CreateTaskExpandedSave"),
        enabled = isEnabled,
    ) {
        Icon(
            imageVector = Icons.Outlined.Check,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize),
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(R.string.save))
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun NotificationPermissionEffect(
    onRevoke: () -> Unit,
    onAccept: @Composable () -> Unit,
) {
    if (LocalInspectionMode.current) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return
    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )

    if (notificationsPermissionState.status.isGranted) {
        onAccept()
    }

    if (notificationsPermissionState.status.shouldShowRationale) {
        NotificationRationale(
            onDismiss = onRevoke,
            onAccept = {
                notificationsPermissionState.launchPermissionRequest()
            }
        )
    }

    LaunchedEffect(notificationsPermissionState) {
        val status = notificationsPermissionState.status
        when {
            status is PermissionStatus.Denied && !status.shouldShowRationale -> {
                notificationsPermissionState.launchPermissionRequest()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTaskExpandedPreview() {
    AtomTheme {
        CreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = "🏃🏻‍♀️ Go out for running!",
            ),
            focusRequester = FocusRequester(),
            onValueChange = {},
            onClickReminder = {},
            onClickDate = {},
            onSave = {},
            onClearReminder = {},
            onClickRecurrence = {},
            onClearRecurrence = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTaskExpandedWithPastReminderErrorPreview() {
    AtomTheme {
        CreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = "🏃🏻‍♀️ Go out for running!",
                reminder = LocalTime.now().minusHours(1),
            ),
            focusRequester = FocusRequester(),
            onValueChange = {},
            onClickReminder = {},
            onClickDate = {},
            onSave = {},
            onClearReminder = {},
            onClickRecurrence = {},
            onClearRecurrence = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateTaskExpandedFilledDataPreview() {
    AtomTheme {
        CreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = "🏃🏻‍♀️ Go out for running!",
                reminder = LocalTime.now().plusHours(4),
                date = LocalDate.now().plusDays(4),
                recurrenceType = RecurrenceType.WEEKLY,
            ),
            focusRequester = FocusRequester(),
            onValueChange = {},
            onClickReminder = {},
            onClickDate = {},
            onSave = {},
            onClearReminder = {},
            onClickRecurrence = {},
            onClearRecurrence = {},
        )
    }
}
