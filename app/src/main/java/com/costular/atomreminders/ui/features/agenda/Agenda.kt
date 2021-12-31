package com.costular.atomreminders.ui.features.agenda

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.ui.components.*
import com.costular.atomreminders.ui.components.create_task.CreateTask
import com.costular.atomreminders.ui.components.create_task.CreateTaskExpanded
import com.costular.atomreminders.ui.components.create_task.CreateTaskResult
import com.costular.atomreminders.ui.dialogs.RemoveTaskDialog
import com.costular.atomreminders.ui.dialogs.TaskActionDialog
import com.costular.atomreminders.ui.theme.AppTheme
import com.costular.atomreminders.ui.util.DateUtils.dayAsText
import com.costular.atomreminders.ui.util.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun Agenda() {
    val bottomState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val viewModel: AgendaViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = AgendaState.Empty)

    BackHandler(enabled = bottomState.isVisible) {
        coroutineScope.launch {
            bottomState.hide()
        }
    }

    LaunchedEffect(viewModel.uiEvents) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is AgendaUiEvents.CloseCreateTask -> {
                    coroutineScope.launch {
                        bottomState.hide()
                    }
                }
            }
        }
    }

    if (state.taskAction != null) {
        TaskActionDialog(
            taskName = state.taskAction?.name,
            onEdit = {

            },
            onDelete = {
                viewModel.actionDelete(requireNotNull(state.taskAction).id)
            },
            onDismissRequest = {
                viewModel.dismissTaskAction()
            }
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

    AtomBottomSheet(
        sheetState = bottomState,
        sheetContent = {
            CreateTask(
                onSave = {
                    viewModel.createTask(
                        it.name,
                        it.date,
                        it.reminder
                    )
                }
            )
        }
    ) {
        Scaffold(
            bottomBar = {
                CreateTask(
                    onClick = {
                        coroutineScope.launch {
                            bottomState.show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.contentMargin)
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
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
                        HabitList(
                            tasks = tasks.data,
                            onClick = { task ->
                                viewModel.openTaskAction(task)
                            },
                            onMarkHabit = { id, isMarked -> viewModel.onMarkTask(id, isMarked) },
                            modifier = Modifier.fillMaxSize(),
                            date = state.selectedDay
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
private fun CreateTask(
    onSave: (CreateTaskResult) -> Unit,
) {
    CreateTaskExpanded(
        value = "Whatever",
        date = LocalDate.now(),
        onSave = onSave,
        modifier = Modifier.fillMaxWidth()
    )
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
                    // TODO: 26/6/21 open calendar
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
            Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
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
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
    }
}