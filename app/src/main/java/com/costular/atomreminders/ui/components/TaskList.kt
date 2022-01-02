package com.costular.atomreminders.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.protobuf.Empty
import com.airbnb.lottie.compose.*
import com.costular.atomreminders.R
import com.costular.atomreminders.domain.model.Task
import com.costular.atomreminders.ui.theme.AppTheme
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
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tasks) { task ->
                TaskCard(
                    title = task.name,
                    onMark = { onMarkTask(task.id, !task.isDone) },
                    onOpen = { onClick(task) },
                    reminder = task.reminder,
                    isFinished = task.isDone
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
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition,
        progress,
        modifier = modifier,
    )
}