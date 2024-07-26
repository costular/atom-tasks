package com.costular.atomtasks.agenda.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.agenda.actions.TaskActionsResult
import com.costular.atomtasks.agenda.destinations.TasksActionsBottomSheetDestination
import com.costular.atomtasks.core.ui.mvi.EventObserver
import com.costular.atomtasks.core.ui.utils.DevicesPreview
import com.costular.atomtasks.core.ui.utils.generateWindowSizeClass
import com.costular.atomtasks.review.ui.ReviewHandler
import com.costular.atomtasks.tasks.dialog.RemoveRecurrentTaskDialog
import com.costular.atomtasks.tasks.dialog.RemoveRecurrentTaskResponse.ALL
import com.costular.atomtasks.tasks.dialog.RemoveRecurrentTaskResponse.THIS
import com.costular.atomtasks.tasks.dialog.RemoveRecurrentTaskResponse.THIS_AND_FUTURES
import com.costular.atomtasks.tasks.dialog.RemoveTaskDialog
import com.costular.atomtasks.tasks.model.Reminder
import com.costular.atomtasks.tasks.model.RecurringRemovalStrategy
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.core.ui.tasks.TaskList
import com.costular.designsystem.components.CircularLoadingIndicator
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.costular.designsystem.util.supportWideScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.collections.immutable.persistentListOf
import org.burnoutcrew.reorderable.ItemPosition
import org.burnoutcrew.reorderable.rememberReorderableLazyListState

const val TestTagHeader = "AgendaTitle"

@Destination
@Composable
fun AgendaScreen(
    navigator: AgendaNavigator,
    setFabOnClick: (() -> Unit) -> Unit,
    resultRecipient: ResultRecipient<TasksActionsBottomSheetDestination, TaskActionsResult>,
    windowSizeClass: WindowSizeClass,
) {
    AgendaScreen(
        navigator = navigator,
        setFabOnClick = setFabOnClick,
        resultRecipient = resultRecipient,
        windowSizeClass = windowSizeClass,
        viewModel = hiltViewModel(),
    )
}

@Composable
internal fun AgendaScreen(
    navigator: AgendaNavigator,
    setFabOnClick: (() -> Unit) -> Unit,
    resultRecipient: ResultRecipient<TasksActionsBottomSheetDestination, TaskActionsResult>,
    windowSizeClass: WindowSizeClass,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EventObserver(viewModel.uiEvents) { event ->
        when (event) {
            is AgendaUiEvents.GoToNewTaskScreen -> {
                if (event.shouldShowNewScreen) {
                    navigator.navigateToDetailScreenForCreateTask(event.date.toString())
                } else {
                    navigator.navigateToCreateTask(event.date.toString())
                }
            }

            is AgendaUiEvents.GoToEditScreen -> {
                if (event.shouldShowNewScreen) {
                    navigator.navigateToDetailScreenToEdit(event.taskId)
                } else {
                    navigator.navigateToEditTask(event.taskId)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        setFabOnClick(viewModel::onCreateTask)
    }

    HandleResultRecipients(
        resultRecipient = resultRecipient,
        onEdit = viewModel::onEditTask,
        onDelete = viewModel::actionDelete,
        onMarkTask = viewModel::onMarkTask,
    )

    ReviewHandler(
        shouldRequestReview = state.shouldShowReviewDialog,
        onFinish = viewModel::onReviewFinished,
    )

    AgendaScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onSelectDate = viewModel::setSelectedDay,
        onSelectToday = viewModel::setSelectedDayToday,
        onMarkTask = viewModel::onMarkTask,
        deleteTask = viewModel::deleteTask,
        deleteRecurringTask = viewModel::deleteRecurringTask,
        dismissDelete = viewModel::dismissDelete,
        openTaskAction = { task ->
            viewModel.onOpenTaskActions()
            navigator.openTaskActions(
                taskId = task.id,
                taskName = task.name,
                isDone = task.isDone,
            )
        },

        onToggleExpandCollapse = viewModel::toggleHeader,
        onMoveTask = viewModel::onMoveTask,
        onDragTask = viewModel::onDragTask,
    )
}

@Composable
private fun HandleResultRecipients(
    resultRecipient: ResultRecipient<TasksActionsBottomSheetDestination, TaskActionsResult>,
    onEdit: (Long) -> Unit,
    onDelete: (Long) -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
) {
    resultRecipient.onNavResult { result ->
        when (result) {
            is NavResult.Canceled -> Unit
            is NavResult.Value -> {
                when (val response = result.value) {
                    is TaskActionsResult.Remove -> {
                        onDelete(response.taskId)
                    }

                    is TaskActionsResult.Edit -> {
                        onEdit(response.taskId)
                    }

                    is TaskActionsResult.MarkAsNotDone -> {
                        onMarkTask(response.taskId, false)
                    }

                    is TaskActionsResult.MarkAsDone -> {
                        onMarkTask(response.taskId, true)
                    }
                }
            }
        }
    }
}

@Suppress("LongMethod", "LongParameterList")
@Composable
fun AgendaScreen(
    state: AgendaState,
    windowSizeClass: WindowSizeClass,
    onSelectDate: (LocalDate) -> Unit,
    onSelectToday: () -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
    deleteTask: (id: Long) -> Unit,
    deleteRecurringTask: (id: Long, strategy: RecurringRemovalStrategy) -> Unit,
    dismissDelete: () -> Unit,
    openTaskAction: (Task) -> Unit,
    onToggleExpandCollapse: () -> Unit,
    onMoveTask: (Int, Int) -> Unit,
    onDragTask: (ItemPosition, ItemPosition) -> Unit,
) {
    if (state.deleteTaskAction is DeleteTaskAction.Shown) {
        if (state.deleteTaskAction.isRecurring) {
            RemoveRecurrentTaskDialog(
                onCancel = dismissDelete,
                onRemove = { response ->
                    val recurringRemovalStrategy = when (response) {
                        THIS -> RecurringRemovalStrategy.SINGLE
                        THIS_AND_FUTURES -> RecurringRemovalStrategy.SINGLE_AND_FUTURE_ONES
                        ALL -> RecurringRemovalStrategy.ALL
                    }
                    deleteRecurringTask(state.deleteTaskAction.taskId, recurringRemovalStrategy)
                }
            )
        } else {
            RemoveTaskDialog(
                onAccept = {
                    deleteTask(state.deleteTaskAction.taskId)
                },
                onCancel = {
                    dismissDelete()
                },
            )
        }
    }

    Column {
        AgendaHeader(
            selectedDay = state.selectedDay,
            onSelectDate = onSelectDate,
            canExpand = windowSizeClass.canExpand,
            isExpanded = state.isHeaderExpanded,
            onToggleExpandCollapse = onToggleExpandCollapse,
            modifier = Modifier.fillMaxWidth(),
            onSelectToday = onSelectToday,
        )
        TasksContent(
            state = state,
            onOpenTask = openTaskAction,
            onMarkTask = onMarkTask,
            modifier = Modifier.supportWideScreen(),
            onMoveTask = onMoveTask,
            onDragTask = onDragTask,
        )
    }
}

@Composable
private fun TasksContent(
    state: AgendaState,
    onOpenTask: (Task) -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
    onDragTask: (ItemPosition, ItemPosition) -> Unit,
    onMoveTask: (Int, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val haptic = LocalHapticFeedback.current

    when (val tasks = state.tasks) {
        is TasksState.Success -> {
            TaskList(
                state = rememberReorderableLazyListState(
                    onMove = onDragTask,
                    onDragEnd = onMoveTask,
                ),
                tasks = tasks.data,
                onClick = onOpenTask,
                onMarkTask = { taskId, isDone ->
                    onMarkTask(taskId, isDone)

                    if (isDone) {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    }
                },
                padding = PaddingValues(
                    start = AppTheme.dimens.contentMargin,
                    end = AppTheme.dimens.contentMargin,
                    top = AppTheme.dimens.spacingLarge,
                    bottom = ContentPaddingForFAB.dp,
                ),
                modifier = modifier
                    .fillMaxSize()
                    .testTag("AgendaTaskList"),
            )
        }

        TasksState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularLoadingIndicator()
            }
        }

        is TasksState.Failure -> {}
        TasksState.Uninitialized -> {}
    }
}

private val WindowSizeClass.canExpand: Boolean
    get() =
        widthSizeClass == WindowWidthSizeClass.Compact ||
                widthSizeClass == WindowWidthSizeClass.Medium

@Suppress("MagicNumber")
@DevicesPreview
@Composable
fun AgendaPreview() {
    val windowSizeClass = generateWindowSizeClass()

    AtomTheme {
        AgendaScreen(
            state = AgendaState(
                tasks = TasksState.Success(
                    persistentListOf(
                        Task(
                            id = 1L,
                            name = "🏋🏼 Go to the gym",
                            createdAt = LocalDate.now(),
                            day = LocalDate.now(),
                            reminder = Reminder(
                                id = 1L,
                                time = LocalTime.of(9, 0),
                                date = LocalDate.now(),
                            ),
                            isDone = false,
                            position = 0,
                            isRecurring = false,
                            recurrenceEndDate = null,
                            recurrenceType = null,
                            parentId = null,
                        ),
                        Task(
                            id = 2L,
                            name = "🎹 Play the piano!",
                            createdAt = LocalDate.now(),
                            day = LocalDate.now(),
                            reminder = Reminder(
                                id = 1L,
                                time = LocalTime.of(9, 0),
                                date = LocalDate.now(),
                            ),
                            isDone = true,
                            position = 0,
                            isRecurring = false,
                            recurrenceEndDate = null,
                            recurrenceType = null,
                            parentId = null,
                        ),
                    ),
                ),
            ),
            windowSizeClass = windowSizeClass,
            onSelectDate = {},
            onSelectToday = {},
            onMarkTask = { _, _ -> },
            onToggleExpandCollapse = {},
            deleteTask = {},
            deleteRecurringTask = { _, _ -> },
            dismissDelete = {},
            openTaskAction = {},
            onMoveTask = { _, _ -> },
            onDragTask = { _, _ -> },
        )
    }
}

private const val ContentPaddingForFAB = 90
