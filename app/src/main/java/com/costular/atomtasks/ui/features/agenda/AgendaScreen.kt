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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import kotlinx.coroutines.flow.collect
import java.time.LocalDate

@Destination(start = true)
@Composable
@Suppress("LongMethod")
fun AgendaScreen(
    navigator: DestinationsNavigator,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(AgendaState.Empty)

    if (state.taskAction != null) {
        TaskActionDialog(
            taskName = state.taskAction?.name,
            isDone = state.taskAction?.isDone ?: false,
            onDelete = {
                viewModel.actionDelete(requireNotNull(state.taskAction).id)
            },
            onDismissRequest = {
                viewModel.dismissTaskAction()
            },
            onDone = {
                viewModel.dismissTaskAction()
                viewModel.onMarkTask(requireNotNull(state.taskAction).id, true)
            },
            onUndone = {
                viewModel.dismissTaskAction()
                viewModel.onMarkTask(requireNotNull(state.taskAction).id, false)
            },
        )
    }

    if (state.deleteTaskAction is DeleteTaskAction.Shown) {
        RemoveTaskDialog(
            onAccept = {
                viewModel.deleteTask((state.deleteTaskAction as DeleteTaskAction.Shown).taskId)
            },
            onCancel = {
                viewModel.dismissDelete()
            },
        )
    }

    Scaffold(
        bottomBar = {
            CreateTask(
                onClick = {
                    navigator.navigate(
                        CreateTaskScreenDestination(date = state.selectedDay.toString()),
                    )
                },
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
                viewModel = viewModel,
            )

            HorizontalCalendar(
                from = state.calendarFromDate,
                until = state.calendarUntilDate,
                modifier = Modifier.padding(bottom = AppTheme.dimens.spacingXLarge),
                selectedDay = state.selectedDay,
                onSelectDay = {
                    viewModel.setSelectedDay(it)
                },
            )

            TasksContent(
                state = state,
                onOpenTask = viewModel::openTaskAction,
                onMarkTask = viewModel::onMarkTask,
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
    viewModel: AgendaViewModel,
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
                    viewModel.setSelectedDay(LocalDate.now())
                }
                .testTag("AgendaTitle"),
        )

        IconButton(
            enabled = state.isPreviousDaySelected,
            onClick = {
                val newDay = state.selectedDay.minusDays(1)
                viewModel.setSelectedDay(newDay)
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
                viewModel.setSelectedDay(newDay)
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
