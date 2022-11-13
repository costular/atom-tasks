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
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.coreui.utils.DateUtils.dayAsText
import com.costular.atomtasks.coreui.utils.DevicesPreview
import com.costular.atomtasks.coreui.utils.generateWindowSizeClass
import com.costular.atomtasks.coreui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.tasks.Reminder
import com.costular.atomtasks.tasks.Task
import com.costular.atomtasks.tasks.TaskList
import com.costular.core.Async
import com.costular.designsystem.components.HorizontalCalendar
import com.costular.designsystem.components.ScreenHeader
import com.costular.designsystem.components.createtask.CreateTask
import com.costular.designsystem.dialogs.RemoveTaskDialog
import com.costular.designsystem.dialogs.TaskActionDialog
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomRemindersTheme
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDate
import java.time.LocalTime

@Destination
@Composable
fun AgendaScreen(
    navigator: AgendaNavigator,
    windowSizeClass: WindowSizeClass,
) {
    AgendaScreen(
        navigator = navigator,
        windowSizeClass = windowSizeClass,
        viewModel = hiltViewModel(),
    )
}

@Composable
@Suppress("LongMethod")
internal fun AgendaScreen(
    navigator: AgendaNavigator,
    windowSizeClass: WindowSizeClass,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(AgendaState.Empty)

    AgendaScreen(
        state = state,
        windowSizeClass = windowSizeClass,
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
    windowSizeClass: WindowSizeClass,
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

@DevicesPreview
@Composable
fun AgendaPreview() {
    val windowSizeClass = generateWindowSizeClass()

    AtomRemindersTheme {
        AgendaScreen(
            state = AgendaState(
                tasks = Async.Success(
                    listOf(
                        Task(
                            id = 1L,
                            name = "🏋🏼 Go to the gym",
                            createdAt = LocalDate.now(),
                            day = LocalDate.now(),
                            reminder = Reminder(
                                id = 1L,
                                time = LocalTime.of(9, 0),
                                isEnabled = true,
                                date = null,
                            ),
                            isDone = false,
                        ),
                        Task(
                            id = 1L,
                            name = "🎹 Play the piano!",
                            createdAt = LocalDate.now(),
                            day = LocalDate.now(),
                            reminder = Reminder(
                                id = 1L,
                                time = LocalTime.of(9, 0),
                                isEnabled = true,
                                date = null,
                            ),
                            isDone = true,
                        ),
                    ),
                ),
            ),
            windowSizeClass = windowSizeClass,
            onSelectDate = {},
            actionDelete = {},
            dismissTaskAction = {},
            onMarkTask = { _, _ -> },
            deleteTask = {},
            dismissDelete = {},
            onCreateTask = {},
            openTaskAction = {},
            onEditAction = {},
        )
    }
}
