package com.costular.atomreminders.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.costular.atomreminders.domain.model.Task
import java.time.LocalDate

@Composable
fun HabitList(
    tasks: List<Task>,
    onClick: (Task) -> Unit,
    onMarkHabit: (taskId: Long, isDone: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    date: LocalDate = LocalDate.now(),
    listState: LazyListState = rememberLazyListState(),
    padding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(tasks) { task ->
            TaskCard(
                title = task.name,
                onMark = { onMarkHabit(task.id, !task.isDone) },
                onOpen = { onClick(task) },
                reminder = task.reminder,
                isFinished = task.isDone
            )
        }
    }

}