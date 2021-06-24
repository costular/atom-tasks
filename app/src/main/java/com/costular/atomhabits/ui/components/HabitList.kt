package com.costular.atomhabits.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.costular.atomhabits.domain.model.Habit

@Composable
fun HabitList(
    habits: List<Habit>,
    onClick: (Habit) -> Unit,
    onMarkHabit: (isMarked: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    padding: PaddingValues = PaddingValues(0.dp)
) {
    LazyColumn(
        modifier = modifier,
        state = listState,
        contentPadding = padding,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(habits) { habit ->
            HabitCard(
                title = habit.name,
                isFinished = habit.isFinishedForToday,
                repetition = habit.repetition,
                onMark = onMarkHabit,
                onOpen = { onClick(habit) }
            )
        }
    }

}