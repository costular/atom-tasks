package com.costular.atomtasks.ui.features.agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.ui.components.HorizontalCalendar
import com.costular.atomtasks.ui.components.ScreenHeader
import com.costular.atomtasks.ui.components.TaskList
import com.costular.atomtasks.ui.components.createtask.CreateTask
import com.costular.atomtasks.ui.dialogs.RemoveTaskDialog
import com.costular.atomtasks.ui.dialogs.TaskActionDialog
import com.costular.atomtasks.ui.features.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.util.DateUtils.dayAsText
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate

@Destination(start = true)
@Composable
@Suppress("LongMethod")
fun AgendaScreen(
    navigator: DestinationsNavigator,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(AgendaState.Empty)

    AgendaScreen(
        state = state,
        onSelectDate = viewModel::setSelectedDay,
        actionDelete = viewModel::actionDelete,
        dismissTaskAction = viewModel::dismissTaskAction,
        onMarkTask = viewModel::onMarkTask,
        deleteTask = viewModel::deleteTask,
        dismissDelete = viewModel::dismissDelete,
        onCreateTask = {
            navigator.navigate(
                CreateTaskScreenDestination(date = state.selectedDay.toString()),
            )
        },
        openTaskAction = viewModel::openTaskAction,
    )
}

@Suppress("LongMethod")
@Composable
internal fun AgendaScreen(
    state: AgendaState,
    onSelectDate: (LocalDate) -> Unit,
    actionDelete: (id: Long) -> Unit,
    dismissTaskAction: () -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
    deleteTask: (id: Long) -> Unit,
    dismissDelete: () -> Unit,
    onCreateTask: () -> Unit,
    openTaskAction: (Task) -> Unit,
) {
    if (state.taskAction != null) {
        TaskActionDialog(
            taskName = state.taskAction.name,
            isDone = state.taskAction.isDone,
            onDelete = {
                actionDelete(requireNotNull(state.taskAction).id)
            },
            onDismissRequest = {
                dismissTaskAction()
            },
            onDone = {
                onMarkTask(requireNotNull(state.taskAction).id, true)
            },
            onUndone = {
                onMarkTask(requireNotNull(state.taskAction).id, false)
            },
        )
    }

    if (state.deleteTaskAction is DeleteTaskAction.Shown) {
        RemoveTaskDialog(
            onAccept = {
                deleteTask(state.deleteTaskAction.taskId)
            },
            onCancel = {
                dismissDelete()
            },
        )
    }

    Scaffold(
        bottomBar = {
            CreateTask(
                onClick = onCreateTask,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.contentMargin)
                    .testTag("AgendaCreateTask"),
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
        ) {
            DayHeader(
                state = state,
                onSelectDate = onSelectDate,
            )

            HorizontalCalendar(
                from = state.calendarFromDate,
                until = state.calendarUntilDate,
                modifier = Modifier.padding(bottom = AppTheme.dimens.spacingXLarge),
                selectedDay = state.selectedDay,
                onSelectDay = {
                    onSelectDate(it)
                },
            )

            TasksContent(
                state = state,
                onOpenTask = openTaskAction,
                onMarkTask = onMarkTask,
            )
        }
    }
}

@Composable
private fun TasksContent(
    state: AgendaState,
    onOpenTask: (Task) -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
) {
    when (val tasks = state.tasks) {
        is Async.Success -> {
            TaskList(
                tasks = tasks.data,
                onClick = onOpenTask,
                onMarkTask = onMarkTask,
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("AgendaTaskList"),
            )
        }
    }
}

@Composable
private fun DayHeader(
    state: AgendaState,
    onSelectDate: (LocalDate) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val selectedDayText = dayAsText(state.selectedDay)
        ScreenHeader(
            text = selectedDayText,
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = AppTheme.dimens.spacingXLarge,
                    horizontal = AppTheme.dimens.spacingLarge,
                )
                .clickable {
                    onSelectDate(LocalDate.now())
                }
                .testTag("AgendaTitle"),
        )

        IconButton(
            enabled = state.isPreviousDaySelected,
            onClick = {
                val newDay = state.selectedDay.minusDays(1)
                onSelectDate(newDay)
            },
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .testTag("AgendaPrevDay"),
        ) {
            Icon(imageVector = Icons.Outlined.ChevronLeft, contentDescription = null)
        }

        IconButton(
            enabled = state.isNextDaySelected,
            onClick = {
                val newDay = state.selectedDay.plusDays(1)
                onSelectDate(newDay)
            },
            modifier = Modifier
                .width(40.dp)
                .height(40.dp)
                .testTag("AgendaNextDay"),
        ) {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
        }
    }
}
