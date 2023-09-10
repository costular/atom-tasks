package com.costular.atomtasks.agenda

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.core.ui.utils.DateUtils.dayAsText
import com.costular.atomtasks.core.ui.utils.DevicesPreview
import com.costular.atomtasks.core.ui.utils.generateWindowSizeClass
import com.costular.atomtasks.coreui.date.Day
import com.costular.atomtasks.tasks.Reminder
import com.costular.atomtasks.tasks.Task
import com.costular.atomtasks.tasks.TaskList
import com.costular.designsystem.components.CircularLoadingIndicator
import com.costular.designsystem.components.DatePicker
import com.costular.designsystem.components.HorizontalCalendar
import com.costular.designsystem.components.ScreenHeader
import com.costular.designsystem.dialogs.RemoveTaskDialog
import com.costular.designsystem.dialogs.TaskActionDialog
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.costular.designsystem.util.supportWideScreen
import com.ramcosta.composedestinations.annotation.Destination
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.collections.immutable.persistentListOf
import org.burnoutcrew.reorderable.ItemPosition
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import com.costular.atomtasks.core.ui.R.string as S

const val TestTagHeader = "AgendaTitle"

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
internal fun AgendaScreen(
    navigator: AgendaNavigator,
    windowSizeClass: WindowSizeClass,
    viewModel: AgendaViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AgendaScreen(
        state = state,
        windowSizeClass = windowSizeClass,
        onSelectDate = viewModel::setSelectedDay,
        onSelectToday = viewModel::setSelectedDayToday,
        actionDelete = viewModel::actionDelete,
        dismissTaskAction = viewModel::dismissTaskAction,
        onMarkTask = viewModel::onMarkTask,
        deleteTask = viewModel::deleteTask,
        dismissDelete = viewModel::dismissDelete,
        openTaskAction = viewModel::openTaskAction,
        onEditAction = { taskId ->
            viewModel.dismissTaskAction()
            navigator.navigateToEditTask(taskId)
        },
        onToggleExpandCollapse = viewModel::toggleHeader,
        onMoveTask = viewModel::onMoveTask,
        onDragTask = viewModel::onDragTask,
    )
}

@Suppress("LongMethod", "LongParameterList")
@Composable
fun AgendaScreen(
    state: AgendaState,
    windowSizeClass: WindowSizeClass,
    onSelectDate: (LocalDate) -> Unit,
    onSelectToday: () -> Unit,
    actionDelete: (id: Long) -> Unit,
    dismissTaskAction: () -> Unit,
    onMarkTask: (Long, Boolean) -> Unit,
    deleteTask: (id: Long) -> Unit,
    dismissDelete: () -> Unit,
    openTaskAction: (Task) -> Unit,
    onEditAction: (id: Long) -> Unit,
    onToggleExpandCollapse: () -> Unit,
    onMoveTask: (Int, Int) -> Unit,
    onDragTask: (ItemPosition, ItemPosition) -> Unit,
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

    Column {
        AgendaHeader(
            state = state,
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
                    horizontal = AppTheme.dimens.contentMargin,
                    vertical = AppTheme.dimens.spacingLarge,
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

@Suppress("LongMethod")
@Composable
private fun AgendaHeader(
    modifier: Modifier = Modifier,
    state: AgendaState,
    onSelectDate: (LocalDate) -> Unit,
    onSelectToday: () -> Unit,
    canExpand: Boolean,
    isExpanded: Boolean,
    onToggleExpandCollapse: () -> Unit,
) {
    val shadowElevation = if (isExpanded) {
        6.dp
    } else {
        2.dp
    }

    val tonalElevation = if (isExpanded) {
        6.dp
    } else {
        0.dp
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        shadowElevation = shadowElevation,
        tonalElevation = tonalElevation,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val selectedDayText = dayAsText(state.selectedDay.date)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = canExpand, onClick = onToggleExpandCollapse),
                ) {
                    ScreenHeader(
                        text = selectedDayText,
                        modifier = Modifier
                            .testTag(TestTagHeader)
                            .padding(
                                top = AppTheme.dimens.spacingLarge,
                                bottom = AppTheme.dimens.spacingLarge,
                                start = AppTheme.dimens.spacingLarge,
                            ),
                    )

                    val degrees by animateFloatAsState(
                        targetValue = if (isExpanded) 180f else 0f,
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutSlowInEasing,
                        ),
                        label = "Header collapsable arrow",
                    )

                    if (canExpand) {
                        IconButton(onClick = { onToggleExpandCollapse() }) {
                            Icon(
                                imageVector = Icons.Default.ExpandMore,
                                contentDescription = null,
                                modifier = Modifier.rotate(degrees),
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onSelectToday,
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.spacingLarge)
                        .width(40.dp)
                        .height(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Today,
                        contentDescription = stringResource(S.today),
                    )
                }
            }

            AnimatedContent(
                targetState = isExpanded && canExpand,
                label = "Header calendar",
            ) { isCollapsed ->
                if (isCollapsed) {
                    HeaderCalendarCollapsed(state.selectedDay, onSelectDate)
                } else {
                    HeaderCalendarExpanded(state.selectedDay, onSelectDate)
                }
            }
        }
    }
}

@Composable
private fun HeaderCalendarCollapsed(
    selectedDay: Day,
    onSelectDate: (LocalDate) -> Unit,
) {
    DatePicker(
        modifier = Modifier
            .supportWideScreen(480.dp)
            .padding(horizontal = AppTheme.dimens.contentMargin)
            .padding(bottom = AppTheme.dimens.spacingMedium),
        selectedDay = selectedDay.date,
        onDateSelected = onSelectDate,
    )
}

@Composable
private fun HeaderCalendarExpanded(
    selectedDay: Day,
    onSelectDate: (LocalDate) -> Unit,
) {
    HorizontalCalendar(
        modifier = Modifier
            .supportWideScreen()
            .padding(
                start = AppTheme.dimens.spacingLarge,
                end = AppTheme.dimens.spacingLarge,
                bottom = AppTheme.dimens.spacingLarge,
            ),
        selectedDay = selectedDay,
        onSelectDay = onSelectDate,
    )
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
                                isEnabled = true,
                                date = null,
                            ),
                            isDone = false,
                            position = 0,
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
                            position = 0,
                        ),
                    ),
                ),
            ),
            windowSizeClass = windowSizeClass,
            onSelectDate = {},
            onSelectToday = {},
            actionDelete = {},
            dismissTaskAction = {},
            onMarkTask = { _, _ -> },
            onToggleExpandCollapse = {},
            deleteTask = {},
            dismissDelete = {},
            openTaskAction = {},
            onEditAction = {},
            onMoveTask = { _, _ -> },
            onDragTask = { _, _ -> },
        )
    }
}
