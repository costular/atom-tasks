package com.costular.atomtasks.ui.features.agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.ui.components.*
import com.costular.atomtasks.ui.components.create_task.CreateTask
import com.costular.atomtasks.ui.dialogs.RemoveTaskDialog
import com.costular.atomtasks.ui.dialogs.TaskActionDialog
import com.costular.atomtasks.ui.features.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.util.DateUtils.dayAsText
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collect

@Destination(start = true)
@Composable
fun AgendaScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel: AgendaViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = AgendaState.Empty)

    LaunchedEffect(viewModel.uiEvents) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is AgendaUiEvents.CloseCreateTask -> {

                }
            }
        }
    }

    if (state.taskAction != null) {
        TaskActionDialog(
            taskName = state.taskAction?.name,
            isDone = state.taskAction?.isDone
                ?: false, // TODO: 12/1/22 improve this nullability logic
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
            }
        )
    }

    Scaffold(
        bottomBar = {
            CreateTask(
                onClick = {
                    navigator.navigate(CreateTaskScreenDestination.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsWithImePadding()
                    .padding(AppTheme.dimens.contentMargin)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            DayHeader(state, viewModel)

            HorizontalCalendar(
                from = state.calendarFromDate,
                until = state.calendarUntilDate,
                modifier = Modifier.padding(bottom = AppTheme.dimens.spacingXLarge),
                selectedDay = state.selectedDay,
                onSelectDay = {
                    viewModel.setSelectedDay(it)
                }
            )

            when (val tasks = state.tasks) {
                is Async.Success -> {
                    TaskList(
                        tasks = tasks.data,
                        onClick = { task ->
                            viewModel.openTaskAction(task)
                        },
                        onMarkTask = { id, isMarked -> viewModel.onMarkTask(id, isMarked) },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        val selectedDayText = dayAsText(state.selectedDay)
        ScreenHeader(
            text = selectedDayText,
            modifier = Modifier
                .weight(1f)
                .padding(
                    vertical = AppTheme.dimens.spacingXLarge,
                    horizontal = AppTheme.dimens.spacingLarge
                )
                .clickable {
                    viewModel.setSelectedDay(LocalDate.now())
                }
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
        ) {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
        }
    }
}