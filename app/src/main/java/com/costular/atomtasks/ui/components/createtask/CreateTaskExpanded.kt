package com.costular.atomtasks.ui.components.createtask

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Today
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.components.RemovableChip
import com.costular.atomtasks.ui.dialogs.DatePickerDialog
import com.costular.atomtasks.ui.dialogs.timepicker.TimePickerDialog
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DateUtils.dayAsText
import com.costular.atomtasks.ui.util.DateUtils.timeAsText
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.LocalTime

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
private fun CreateTaskExpanded(
    state: CreateTaskExpandedState,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    onClickDate: () -> Unit,
    onClickReminder: () -> Unit,
    onClearReminder: () -> Unit,
    onSave: () -> Unit,
) {
    Column(modifier = modifier.padding(AppTheme.dimens.contentMargin)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CreateTaskInput(
                value = state.name,
                focusRequester = focusRequester,
                onValueChange = onValueChange,
                shouldShowSend = state.shouldShowSend,
                onSave = onSave,
            )
        }

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Row(modifier = Modifier.fillMaxWidth()) {
            RemovableChip(
                title = dayAsText(state.date),
                icon = Icons.Outlined.Today,
                isSelected = false,
                onClick = onClickDate,
                onClear = onClearReminder,
            )

            Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

            val reminderText = if (state.reminder != null) {
                timeAsText(state.reminder)
            } else {
                stringResource(R.string.create_task_set_reminder)
            }

            RemovableChip(
                title = reminderText,
                icon = Icons.Outlined.Alarm,
                isSelected = state.reminder != null,
                onClick = onClickReminder,
                onClear = onClearReminder,
            )
        }
    }
}

@Composable
private fun RowScope.CreateTaskInput(
    value: String,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    shouldShowSend: Boolean,
    onSave: () -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                stringResource(R.string.agenda_create_task_name),
                style = MaterialTheme.typography.h6,
            )
        },
        modifier = Modifier.Companion
            .weight(1f)
            .testTag("CreateTaskInput")
            .focusRequester(focusRequester),
        textStyle = MaterialTheme.typography.h6,
        maxLines = 2,
        trailingIcon = {
            AnimatedVisibility(
                visible = shouldShowSend,
                enter = scaleIn(spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
                exit = scaleOut(spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
            ) {
                IconButton(
                    onClick = onSave,
                    modifier = Modifier
                        .padding(AppTheme.dimens.spacingSmall)
                        .clip(MaterialTheme.shapes.small)
                        .background(MaterialTheme.colors.secondary),
                ) {
                    Icon(imageVector = Icons.Outlined.Check, contentDescription = null)
                }
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = if (shouldShowSend) ImeAction.Done else ImeAction.None,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onSave()
            },
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun CreateTaskExpandedPreview() {
    AtomRemindersTheme {
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
