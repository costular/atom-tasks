package com.costular.atomtasks.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.costular.commonui.R
import com.costular.commonui.theme.AppTheme
import com.costular.commonui.theme.AtomRemindersTheme
import java.time.LocalDate

@Composable
fun TaskList(
    tasks: List<Task>,
    onClick: (Task) -> Unit,
    onMarkTask: (taskId: Long, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    padding: PaddingValues = PaddingValues(0.dp),
) {
    if (tasks.isEmpty()) {
        Empty(modifier.padding(AppTheme.dimens.contentMargin))
    } else {
        LazyColumn(
            modifier = modifier,
            state = listState,
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(tasks) { task ->
                TaskCard(
                    title = task.name,
                    onMark = { onMarkTask(task.id, !task.isDone) },
                    onOpen = { onClick(task) },
                    reminder = task.reminder,
                    isFinished = task.isDone,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
fun Empty(
    modifier: Modifier = Modifier,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty))
    val progress by animateLottieCompositionAsState(
        composition,
    )

    LottieAnimation(
        composition,
        progress,
        modifier = modifier,
    )
}

@Preview
@Composable
fun TaskListEmpty() {
    Empty(Modifier.fillMaxWidth())
}

@Preview
@Composable
fun TaskList() {
    AtomRemindersTheme {
        TaskList(
            modifier = Modifier.fillMaxWidth(),
            tasks = listOf(
                Task(
                    id = 1L,
                    name = "Task1",
                    createdAt = LocalDate.now(),
                    day = LocalDate.now(),
                    reminder = null,
                    isDone = false,
                ),
                Task(
                    id = 1L,
                    name = "Task2",
                    createdAt = LocalDate.now(),
                    day = LocalDate.now(),
                    reminder = null,
                    isDone = false,
                ),
                Task(
                    id = 1L,
                    name = "Task3",
                    createdAt = LocalDate.now(),
                    day = LocalDate.now(),
                    reminder = null,
                    isDone = true,
                ),
            ),
            onClick = {},
            onMarkTask = { _, _ -> },
        )
    }
}
