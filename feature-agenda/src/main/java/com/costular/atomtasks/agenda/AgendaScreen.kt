package com.costular.atomtasks.agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.coreui.utils.DateUtils.dayAsText
import com.costular.atomtasks.coreui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.tasks.Task
import com.costular.commonui.components.HorizontalCalendar
import com.costular.commonui.components.ScreenHeader
import com.costular.atomtasks.tasks.TaskList
import com.costular.commonui.components.createtask.CreateTask
import com.costular.commonui.dialogs.RemoveTaskDialog
import com.costular.commonui.dialogs.TaskActionDialog
import com.costular.commonui.theme.AppTheme
import com.costular.core.Async
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDate

@Destination
@Composable
fun AgendaScreen(
    navigator: AgendaNavigator,
) {
    AgendaScreen(
        navigator = navigator,
        viewModel = hiltViewModel(),
    )
}

@Composable
@Suppress("LongMethod")
internal fun AgendaScreen(
    navigator: AgendaNavigator,
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
            navigator.navigateToCreateTask(date = state.selectedDay.toString(), text = null)
        },
        openTaskAction = viewModel::openTaskAction,
        onEditAction = { taskId ->
            viewModel.dismissTaskAction()
            navigator.navigateToEditTask(taskId)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("LongMethod")
@Composable
fun AgendaScreen(
    state: AgendaState,
    onSelectDate: (LocalDate) -> Unit,
    actionDelete: (id: Long) -> Unit,
    dismissTaskAction: () -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
    deleteTask: (id: Long) -> Unit,
    dismissDelete: () -> Unit,
    onCreateTask: () -> Unit,
    openTaskAction: (Task) -> Unit,
    onEditAction: (id: Long) -> Unit,
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
            onEdit = {
                onEditAction(requireNotNull(state.taskAction).id)
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
        floatingActionButton = {
            CreateTask(
                onClick = onCreateTask,
                modifier = Modifier
                    .padding(AppTheme.dimens.contentMargin)
                    .testTag("AgendaCreateTask"),
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                    .padding(horizontal = AppTheme.dimens.contentMargin)
                    .testTag("AgendaTaskList"),
            )
        }
        is Async.Failure -> {}
        Async.Loading -> {}
        Async.Uninitialized -> {}
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
