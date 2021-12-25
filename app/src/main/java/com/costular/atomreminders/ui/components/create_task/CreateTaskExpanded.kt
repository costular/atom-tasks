package com.costular.atomreminders.ui.components.create_task

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomreminders.ui.components.Chip
import com.costular.atomreminders.ui.theme.AppTheme
import com.costular.atomreminders.ui.theme.AtomRemindersTheme
import com.costular.atomreminders.ui.util.DateTimeFormatters
import com.costular.atomreminders.ui.util.rememberFlowWithLifecycle
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
    onClickReminder: () -> Unit,
    onSave: () -> Unit,
) {
    Column(modifier = modifier.padding(AppTheme.dimens.contentMargin)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            BasicTextField(
                value = state.name,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                textStyle = MaterialTheme.typography.h6,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
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
                    modifier = Modifier.size(36.dp),
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

        Spacer(Modifier.height(AppTheme.dimens.spacingMedium))

        Row(modifier = Modifier.fillMaxWidth()) {
            Chip(
                isSelected = state.shouldShowDateSelection,
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

            Chip(
                isSelected = state.shouldShowReminderSelection,
                onClick = onClickReminder,
            ) {
                Icon(
                    Icons.Outlined.Alarm,
                    contentDescription = null,
                    modifier = Modifier.size(AppTheme.ChipIconSize)
                )
                Spacer(Modifier.width(AppTheme.dimens.spacingMedium))
                Text(
                    "Reminder",
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
                        // TODO: 24/12/21
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
        )
    }
}