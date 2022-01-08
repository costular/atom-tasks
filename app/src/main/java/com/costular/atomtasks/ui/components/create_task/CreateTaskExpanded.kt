package com.costular.atomtasks.ui.components.create_task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.components.Chip
import com.costular.atomtasks.ui.components.ExpandableChip
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DateTimeFormatters
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect
import java.time.LocalDate
import java.time.LocalTime

@ExperimentalComposeUiApi
@OptIn(ExperimentalAnimationApi::class)
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

    CreateTaskExpanded(
        state = state,
        modifier = modifier,
        onValueChange = { viewModel.setName(it) },
        onClickDate = { viewModel.selectTaskData(TaskDataSelection.Date) },
        onSetDate = { viewModel.setDate(it) },
        onSetTime = { viewModel.setReminder(it) },
        onClickReminder = { viewModel.selectTaskData(TaskDataSelection.Reminder) },
        onSave = {
            viewModel.requestSave()
        },
    )
}

@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
private fun CreateTaskExpanded(
    state: CreateTaskExpandedState,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    onClickDate: () -> Unit,
    onSetDate: (LocalDate) -> Unit,
    onSetTime: (LocalTime?) -> Unit,
    onClickReminder: () -> Unit,
    onSave: () -> Unit,
) {
    Column(modifier = modifier.padding(AppTheme.dimens.contentMargin)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            val imeAction = if (state.shouldShowSend) ImeAction.Done else ImeAction.None

            OutlinedTextField(
                value = state.name,
                onValueChange = onValueChange,
                placeholder = {
                    Text(
                        stringResource(R.string.agenda_create_task_name),
                        style = MaterialTheme.typography.h6,
                    )
                },
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.h6,
                keyboardOptions = KeyboardOptions(imeAction = imeAction),
                keyboardActions = KeyboardActions(onDone = {
                    onSave()
                })
            )

            Spacer(Modifier.width(AppTheme.dimens.spacingLarge))

            AnimatedVisibility(
                visible = state.shouldShowSend,
                enter = scaleIn(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
                exit = scaleOut(animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)),
            ) {
                FloatingActionButton(
                    onClick = onSave,
                    modifier = Modifier.size(48.dp),
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        pressedElevation = 0.dp,
                        hoveredElevation = 0.dp,
                        focusedElevation = 0.dp
                    ),
                ) {
                    Icon(Icons.Outlined.Check, contentDescription = null)
                }
            }
        }

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Row(modifier = Modifier.fillMaxWidth()) {
            ExpandableChip(
                isExpanded = state.shouldShowDateSelection,
                onClick = onClickDate,
            ) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.ChipIconSize)
                )
                Spacer(Modifier.width(AppTheme.dimens.spacingMedium))
                Text(
                    DateTimeFormatters.dateFormatter.format(state.date),
                    style = MaterialTheme.typography.body1,
                )
            }

            Spacer(Modifier.width(16.dp))

            ExpandableChip(
                isExpanded = state.shouldShowReminderSelection,
                onClick = onClickReminder,
            ) {
                Icon(
                    Icons.Outlined.Alarm,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.ChipIconSize)
                )
                Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

                val reminderText = if (state.reminder != null) {
                    DateTimeFormatters.timeFormatter.format(state.reminder)
                } else {
                    stringResource(R.string.no_reminder)
                }

                Text(
                    reminderText,
                    style = MaterialTheme.typography.body1,
                )
            }
        }

        when {
            state.shouldShowDateSelection -> {
                TaskDateData(
                    date = state.date,
                    onSelectDate = {
                        onSetDate(it)
                    }
                )
            }
            state.shouldShowReminderSelection -> {
                TaskReminderData(
                    reminder = state.reminder ?: LocalTime.now(),
                    onSelectReminder = {
                        onSetTime(it)
                    }
                )
            }
        }
    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalComposeUiApi::class)
@Preview(showBackground = true)
@Composable
fun CreateTaskExpandedPreview() {
    AtomRemindersTheme {
        CreateTaskExpanded(
            state = CreateTaskExpandedState(
                name = "🏃🏻‍♀️ Go out for running!",
            ),
            onValueChange = {},
            onClickReminder = {},
            onClickDate = {},
            onSave = {},
            onSetDate = {},
            onSetTime = {},
        )
    }
}