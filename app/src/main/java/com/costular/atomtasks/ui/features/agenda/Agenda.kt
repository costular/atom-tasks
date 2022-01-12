package com.costular.atomtasks.ui.features.agenda

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Today
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.domain.Async
import com.costular.atomtasks.ui.components.*
import com.costular.atomtasks.ui.components.create_task.CreateTask
import com.costular.atomtasks.ui.components.create_task.CreateTaskExpanded
import com.costular.atomtasks.ui.components.create_task.CreateTaskResult
import com.costular.atomtasks.ui.dialogs.RemoveTaskDialog
import com.costular.atomtasks.ui.dialogs.TaskActionDialog
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.util.DateUtils.dayAsText
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@ExperimentalMaterialApi
@Composable
fun Agenda() {
    val keyboardController = LocalSoftwareKeyboardController.current
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
                        keyboardController?.hide()
                        try {
                            bottomState.hide()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    if (state.taskAction != null) {
        TaskActionDialog(
            taskName = state.taskAction?.name,
            isDone = state.taskAction?.isDone ?: false, // TODO: 12/1/22 improve this nullability logic
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

    AtomBottomSheet(
        sheetState = bottomState,
        sheetContent = {
            CreateTask(
                state.selectedDay,
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
                            bottomState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsWithImePadding()
                        .padding(AppTheme.dimens.contentMargin)
                )
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(contentPadding)
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
}

@ExperimentalComposeUiApi
@Composable
private fun CreateTask(
    date: LocalDate,
    onSave: (CreateTaskResult) -> Unit,
) {
    CreateTaskExpanded(
        value = "",
        date = date,
        onSave = onSave,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsWithImePadding()
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