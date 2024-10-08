@file:Suppress("TooManyFunctions")

package com.atomtasks.feature.detail

import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.core.ui.mvi.EventObserver
import com.costular.atomtasks.core.ui.utils.DateUtils.dayAsText
import com.costular.atomtasks.core.ui.utils.ofLocalizedTime
import com.costular.atomtasks.tasks.createtask.RecurrenceTypePickerDialog
import com.costular.atomtasks.tasks.format.localized
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.designsystem.dialogs.DatePickerDialog
import com.costular.designsystem.dialogs.TimePickerDialog
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.LocalTime
import com.costular.atomtasks.core.ui.R.string as S

private const val FieldMaxLines = 5
private const val FieldMinLines = 1

@OptIn(ExperimentalFoundationApi::class)
@Destination<TaskDetailGraph>(
    start = true,
    navArgs = TaskDetailNavArgs::class,
)
@Composable
fun TaskDetailScreen(
    navigator: DestinationsNavigator,
    viewModel: TaskDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    EventObserver(viewModel.uiEvents) { event ->
        when (event) {
            is TaskDetailUiEvent.Close -> navigator.navigateUp()

            is TaskDetailUiEvent.NavigateToExactAlarmSettings -> {
                if (Build.VERSION.SDK_INT >= VERSION_CODES.S) {
                    context.startActivity(Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
                }
            }
        }
    }

    TaskDetailScreen(
        uiState = uiState,
        onSelectDate = viewModel::onSelectDateClicked,
        onSelectReminder = viewModel::onSelectReminderClicked,
        onSelectRecurrence = viewModel::onSelectRecurrenceClicked,
        onRecurrenceChanged = viewModel::onRecurrenceChanged,
        onSave = viewModel::save,
        onCloseSelectDate = viewModel::closeSelectDate,
        onDateChanged = viewModel::onDateChanged,
        onClearReminder = viewModel::onReminderRemoved,
        onSetReminder = viewModel::onReminderChanged,
        onCloseSelectReminder = viewModel::closeSelectReminder,
        onExactAlarmSettingChanged = viewModel::exactAlarmSettingChanged,
        navigateToExactAlarmSettings = viewModel::navigateToExactAlarmSettings,
        onCloseSelectRecurrence = viewModel::closeSelectRecurrence,
        onCancelRecurringEdition = viewModel::cancelRecurringEdition,
        onConfirmRecurringEdition = viewModel::confirmRecurringEdition,
        onClose = viewModel::onClose,
        onClearRecurrence = viewModel::clearRecurrence,
    )
}

@Suppress("LongParameterList")
@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalFoundationApi
@Composable
fun TaskDetailScreen(
    uiState: TaskDetailUiState,
    onClose: () -> Unit,
    onSelectDate: () -> Unit,
    onSelectReminder: () -> Unit,
    onClearReminder: () -> Unit,
    onSelectRecurrence: () -> Unit,
    onClearRecurrence: () -> Unit,
    onRecurrenceChanged: (RecurrenceType?) -> Unit,
    onSave: () -> Unit,
    onCloseSelectDate: () -> Unit,
    onDateChanged: (LocalDate) -> Unit,
    onSetReminder: (LocalTime) -> Unit,
    onCloseSelectReminder: () -> Unit,
    onExactAlarmSettingChanged: () -> Unit,
    navigateToExactAlarmSettings: () -> Unit,
    onCloseSelectRecurrence: () -> Unit,
    onCancelRecurringEdition: () -> Unit,
    onConfirmRecurringEdition: (EditRecurringTaskResponse) -> Unit,
) {
    if (uiState.showSetDate) {
        DatePickerDialog(
            onDismiss = onCloseSelectDate,
            currentDate = uiState.date,
            onDateSelected = onDateChanged,
        )
    }

    if (uiState.showSetReminder) {
        if (uiState.shouldShowExactAlarmRationale) {
            ExactAlarmRationale(
                onDismiss = onCloseSelectReminder,
                navigateToExactAlarmSettings = navigateToExactAlarmSettings,
                onPermissionStateChanged = onExactAlarmSettingChanged
            )
        } else {
            NotificationPermissionEffect(
                onRevoke = onCloseSelectReminder
            ) {
                TimePickerDialog(
                    onDismiss = onCloseSelectReminder,
                    selectedTime = uiState.reminder,
                    onSelectTime = onSetReminder,
                )
            }
        }
    }

    if (uiState.showSetRecurrence) {
        RecurrenceTypePickerDialog(
            recurrenceType = uiState.recurrenceType,
            onRecurrenceTypeSelected = onRecurrenceChanged,
            onDismissRequest = onCloseSelectRecurrence,
        )
    }

    if (uiState.taskToSave != null) {
        EditRecurringTaskConfirmDialog(
            onCancel = onCancelRecurringEdition,
            onEdit = onConfirmRecurringEdition,
        )
    }

    TaskDetailContent(
        uiState = uiState,
        onSelectDate = onSelectDate,
        onSelectReminder = onSelectReminder,
        onSelectRecurrence = onSelectRecurrence,
        onSave = onSave,
        onClearReminder = onClearReminder,
        onClose = onClose,
        onClearRecurrence = onClearRecurrence,
    )
}

@ExperimentalFoundationApi
@Composable
private fun TaskDetailContent(
    uiState: TaskDetailUiState,
    onSelectDate: () -> Unit,
    onSelectReminder: () -> Unit,
    onClearReminder: () -> Unit,
    onSelectRecurrence: () -> Unit,
    onClearRecurrence: () -> Unit,
    onClose: () -> Unit,
    onSave: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(focusRequester) {
        focusRequester.requestFocus()
    }

    Column(Modifier.fillMaxSize()) {
        Header(
            onClose = onClose,
            onSave = onSave
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        TaskInput(
            name = uiState.name,
            focusRequester = focusRequester
        )

        TaskDateSection(
            localDate = uiState.date,
            onSelectDate = onSelectDate,
        )

        TaskReminderSection(
            reminder = uiState.reminder,
            onSelectReminder = onSelectReminder,
            onClearReminder = onClearReminder,
        )

        TaskRecurrenceSection(
            recurrenceType = uiState.recurrenceType,
            onSelectRecurrence = onSelectRecurrence,
            onClearRecurrence = onClearRecurrence,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))
    }
}

@Composable
private fun Header(onClose: () -> Unit, onSave: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier.padding(start = 2.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null
            )
        }

        Button(
            onClick = onSave,
            modifier = Modifier.padding(end = AppTheme.dimens.spacingLarge)
        ) {
            Text(
                text = stringResource(S.save)
            )
        }
    }
}

@Composable
private fun ColumnScope.TaskRecurrenceSection(
    recurrenceType: RecurrenceType?,
    onSelectRecurrence: () -> Unit,
    onClearRecurrence: () -> Unit,
) {
    TaskDetailSection(sectionName = stringResource(S.task_detail_recurrence_subhead)) {
        Spacer(Modifier.height(AppTheme.dimens.spacingMedium))

        Field(
            icon = Icons.Outlined.Repeat,
            onClick = onSelectRecurrence,
            onClear = onClearRecurrence,
            text = recurrenceType.localized(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.spacingLarge)
        )
    }
}

@Composable
private fun ColumnScope.TaskDateSection(
    localDate: LocalDate,
    onSelectDate: () -> Unit,
) {
    TaskDetailSection(sectionName = stringResource(S.task_detail_day_subhead)) {
        Spacer(Modifier.height(AppTheme.dimens.spacingMedium))

        Field(
            icon = Icons.Outlined.CalendarToday,
            onClick = onSelectDate,
            text = dayAsText(localDate),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

    }
}

@Composable
private fun ColumnScope.TaskReminderSection(
    reminder: LocalTime?,
    onSelectReminder: () -> Unit,
    onClearReminder: () -> Unit,
) {
    TaskDetailSection(sectionName = stringResource(S.task_detail_reminder_subhead)) {
        if (reminder != null) {
            Spacer(Modifier.height(AppTheme.dimens.spacingMedium))

            Field(
                icon = Icons.Outlined.Alarm,
                onClick = onSelectReminder,
                text = reminder.ofLocalizedTime(),
                isClearable = true,
                onClear = onClearReminder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.spacingLarge)
            )
        } else {
            TextButton(
                onClick = onSelectReminder,
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                Text(text = stringResource(S.task_detail_reminder_add_reminder))
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
private fun TaskInput(
    name: TextFieldState,
    focusRequester: FocusRequester,
) {
    Box {
        if (name.text.isEmpty()) {
            Text(
                text = stringResource(S.task_detail_task_name_placeholder),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimens.spacingLarge),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        BasicTextField(
            state = name,
            lineLimits = TextFieldLineLimits.MultiLine(FieldMinLines, FieldMaxLines),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.spacingLarge)
                .focusRequester(focusRequester),
            textStyle = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
fun ColumnScope.TaskDetailSection(
    sectionName: String,
    content: @Composable () -> Unit,
) {
    SectionSeparator(
        Modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimens.spacingLarge)
    )

    Text(
        text = sectionName,
        style = MaterialTheme.typography.titleSmall,
        modifier = Modifier.padding(horizontal = AppTheme.dimens.spacingLarge)
    )

    content()
}

@Composable
fun Field(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isClearable: Boolean = false,
    onClear: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(percent = 50),
            )
            .clip(RoundedCornerShape(percent = 50))
            .clickable(onClick = onClick)
            .padding(
                vertical = 12.dp,
                horizontal = AppTheme.dimens.spacingLarge,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1f),
        )

        if (isClearable) {
            CompositionLocalProvider(
                value = LocalMinimumInteractiveComponentSize provides Dp.Unspecified
            ) {
                IconButton(
                    onClick = onClear,
                    modifier = Modifier.size(18.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun SectionSeparator(modifier: Modifier = Modifier) {
    HorizontalDivider(modifier)
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun TaskDetailScreenPreview(
    @PreviewParameter(TaskDetailPreviewParameterProvider::class) uiState: TaskDetailUiState,
) {
    AtomTheme {
        TaskDetailScreen(
            uiState = uiState,
            onSave = {},
            onSelectReminder = {},
            onSelectDate = {},
            onSelectRecurrence = {},
            onDateChanged = {},
            onCloseSelectDate = {},
            onClearReminder = {},
            onCloseSelectReminder = {},
            onExactAlarmSettingChanged = {},
            onSetReminder = {},
            navigateToExactAlarmSettings = {},
            onRecurrenceChanged = {},
            onCloseSelectRecurrence = {},
            onCancelRecurringEdition = {},
            onConfirmRecurringEdition = {},
            onClose = {},
            onClearRecurrence = {},
        )
    }
}
