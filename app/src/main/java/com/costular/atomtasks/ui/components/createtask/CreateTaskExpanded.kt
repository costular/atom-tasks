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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.components.ExpandableChip
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DateTimeFormatters
import com.costular.atomtasks.ui.util.DateUtils.dayAsText
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
            CreateTaskInput(
                value = state.name,
                onValueChange = onValueChange,
                shouldShowSend = state.shouldShowSend,
                onSave = onSave,
            )
        }

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Row(modifier = Modifier.fillMaxWidth()) {
            DateChip(
                shouldShowDateSelection = state.shouldShowDateSelection,
                date = state.date,
                onClickDate = onClickDate,
            )

            Spacer(Modifier.width(16.dp))

            ReminderChip(
                shouldShowReminderSelection = state.shouldShowReminderSelection,
                reminder = state.reminder,
                onClickReminder = onClickReminder,
            )
        }

        when {
            state.shouldShowDateSelection -> {
                TaskDateData(
                    date = state.date,
                    onSelectDate = {
                        onSetDate(it)
                    },
                )
            }
            state.shouldShowReminderSelection -> {
                TaskReminderData(
                    reminder = state.reminder ?: LocalTime.now(),
                    onSelectReminder = {
                        onSetTime(it)
                    },
                )
            }
        }
    }
}

@Composable
private fun ReminderChip(
    shouldShowReminderSelection: Boolean,
    reminder: LocalTime?,
    onClickReminder: () -> Unit,
) {
    ExpandableChip(
        isExpanded = shouldShowReminderSelection,
        onClick = onClickReminder,
    ) {
        Icon(
            Icons.Outlined.Alarm,
            contentDescription = null,
            modifier = Modifier.size(AppTheme.ChipIconSize),
        )
        Spacer(Modifier.width(AppTheme.dimens.spacingMedium))

        val reminderText = if (reminder != null) {
            DateTimeFormatters.timeFormatter.format(reminder)
        } else {
            stringResource(R.string.no_reminder)
        }

        Text(
            reminderText,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun DateChip(
    shouldShowDateSelection: Boolean,
    date: LocalDate,
    onClickDate: () -> Unit,
) {
    ExpandableChip(
        isExpanded = shouldShowDateSelection,
        onClick = onClickDate,
    ) {
        Icon(
            Icons.Outlined.CalendarToday,
            contentDescription = null,
            modifier = Modifier.size(AppTheme.ChipIconSize),
        )
        Spacer(Modifier.width(AppTheme.dimens.spacingMedium))
        Text(
            dayAsText(date),
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
private fun RowScope.CreateTaskInput(
    value: String,
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
            .testTag("CreateTaskInput"),
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
            onValueChange = {},
            onClickReminder = {},
            onClickDate = {},
            onSave = {},
            onSetDate = {},
            onSetTime = {},
        )
    }
}
