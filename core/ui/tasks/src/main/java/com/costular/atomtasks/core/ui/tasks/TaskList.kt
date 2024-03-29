package com.costular.atomtasks.core.ui.tasks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.core.ui.R
import com.costular.atomtasks.core.ui.utils.VariantsPreview
import com.costular.atomtasks.tasks.model.Reminder
import com.costular.atomtasks.tasks.model.Task
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import java.time.LocalDate
import java.time.LocalTime
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.ReorderableLazyListState
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskList(
    tasks: List<Task>,
    onClick: (Task) -> Unit,
    onMarkTask: (taskId: Long, isDone: Boolean) -> Unit,
    state: ReorderableLazyListState,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
) {
    if (tasks.isEmpty()) {
        Empty(modifier.padding(AppTheme.dimens.contentMargin))
    } else {
        LazyColumn(
            modifier = modifier
                .reorderable(state)
                .detectReorderAfterLongPress(state),
            state = state.listState,
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(tasks, { it.id }) { task ->
                ReorderableItem(state, key = task.id) { isDragging ->
                    TaskCard(
                        title = task.name,
                        onMark = { onMarkTask(task.id, !task.isDone) },
                        onOpen = { onClick(task) },
                        reminder = task.reminder,
                        isFinished = task.isDone,
                        recurrenceType = task.recurrenceType,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateItemPlacement(),
                        isBeingDragged = isDragging,
                    )
                }
            }
        }
    }
}

@Composable
fun Empty(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.task_list_empty_title),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingMedium))

        Text(
            text = stringResource(R.string.task_list_empty_description),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}

@VariantsPreview
@Preview
@Composable
fun TaskListEmpty() {
    AtomTheme {
        Empty(Modifier.fillMaxWidth())
    }
}

@Suppress("MagicNumber")
@VariantsPreview
@Preview(showBackground = true)
@Composable
private fun TaskListPreview() {
    AtomTheme {
        TaskList(
            state = rememberReorderableLazyListState(onMove = { _, _ -> }),
            modifier = Modifier.fillMaxWidth(),
            tasks = listOf(
                Task(
                    id = 1L,
                    name = "Task1",
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
                    name = "Task2",
                    createdAt = LocalDate.now(),
                    day = LocalDate.now(),
                    reminder = null,
                    isDone = false,
                    position = 0,
                    isRecurring = false,
                    recurrenceEndDate = null,
                    recurrenceType = null,
                    parentId = null,
                ),
                Task(
                    id = 3L,
                    name = "Task3",
                    createdAt = LocalDate.now(),
                    day = LocalDate.now(),
                    reminder = null,
                    isDone = true,
                    position = 0,
                    isRecurring = false,
                    recurrenceEndDate = null,
                    recurrenceType = null,
                    parentId = null,
                ),
            ),
            onClick = {},
            onMarkTask = { _, _ -> },
        )
    }
}
