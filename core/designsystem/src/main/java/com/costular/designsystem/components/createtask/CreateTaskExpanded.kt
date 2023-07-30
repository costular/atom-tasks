package com.costular.designsystem.components.createtask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.core.ui.R
import com.costular.atomtasks.core.ui.utils.DateUtils.dayAsText
import com.costular.atomtasks.core.ui.utils.DateUtils.timeAsText
import com.costular.atomtasks.core.ui.utils.rememberFlowWithLifecycle
import com.costular.designsystem.components.ClearableChip
import com.costular.designsystem.components.PrimaryButton
import com.costular.designsystem.dialogs.DatePickerDialog
import com.costular.designsystem.dialogs.timepicker.TimePickerDialog
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import java.time.LocalDate
import java.time.LocalTime

@Suppress("MagicNumber")
@Composable
fun CreateTaskExpanded(
    value: String,
    date: LocalDate,
    onSave: (CreateTaskResult) -> Unit,
    modifier: Modifier = Modifier,
    reminder: LocalTime? = null,
) {
    val viewModel: CreateTaskExpandedViewModel = viewModel()
    val state by
        rememberFlowWithLifecycle(viewModel.state).collectAsState(CreateTaskExpandedState.Empty)
    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
                is CreateTaskUiEvents.SaveTask -> onSave(event.taskResult)
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
        TimePickerDialog(
            time = state.reminder ?: LocalTime.now(),
            timeSuggestions = listOf(
                LocalTime.of(9, 0),
                LocalTime.of(12, 0),
                LocalTime.of(14, 0),
                LocalTime.of(18, 0),
                LocalTime.of(20, 0),
            ),
            onTimeChange = viewModel::setReminder,
            onCancel = viewModel::closeSelectReminder,
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
        onSave = {
            viewModel.requestSave()
        },
    )
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
    onSave: () -> Unit,
) {
    Column(modifier.padding(AppTheme.dimens.contentMargin)) {
        CreateTaskInput(
            value = state.name,
            focusRequester = focusRequester,
            onValueChange = onValueChange,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Row(modifier = Modifier.fillMaxWidth()) {
            ClearableChip(
                title = dayAsText(state.date),
                icon = Icons.Outlined.Today,
                isSelected = false,
                onClick = onClickDate,
                onClear = onClearReminder,
                modifier = Modifier.testTag("CreateTaskExpandedDate"),
            )

            Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

            val reminderText = if (state.reminder != null) {
                timeAsText(state.reminder)
            } else {
                stringResource(R.string.create_task_set_reminder)
            }

            ClearableChip(
                title = reminderText,
                icon = Icons.Outlined.Alarm,
                isSelected = state.reminder != null,
                onClick = onClickReminder,
                onClear = onClearReminder,
                modifier = Modifier.testTag("CreateTaskExpandedReminder"),
            )
        }

        Spacer(Modifier.height(AppTheme.dimens.spacingHuge))

        SaveButton(
            isEnabled = state.shouldShowSend,
            onSave = onSave,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateTaskInput(
    value: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
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
        modifier = Modifier.Companion
            .fillMaxWidth()
            .testTag("CreateTaskInput")
            .focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.bodyLarge,
        maxLines = 2,
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
        )
    }
}
