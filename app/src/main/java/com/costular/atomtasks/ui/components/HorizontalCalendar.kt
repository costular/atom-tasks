package com.costular.atomtasks.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DateTimeFormatters.dayOfWeekFormatter
import com.costular.atomtasks.ui.util.DateUtils
import java.time.LocalDate
import kotlinx.coroutines.launch

@Composable
fun HorizontalCalendar(
    from: LocalDate,
    until: LocalDate,
    onSelectDay: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    selectedDay: LocalDate = LocalDate.now(),
    lazyListState: LazyListState = rememberLazyListState(),
) {
    val coroutineScope = rememberCoroutineScope()
    val dates = DateUtils.datesBetween(from, until)
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(dates) { item ->
            CalendarDay(
                date = item,
                isSelected = item == selectedDay,
                onClick = { onSelectDay(item) },
            )
        }
    }

    val index = dates.indexOf(selectedDay)
    LaunchedEffect(selectedDay) {
        coroutineScope.launch {
            lazyListState.animateScrollToItem(index)
        }
    }
}

@Composable
private fun CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val day = date.dayOfMonth
    val weekDay = dayOfWeekFormatter.format(date.dayOfWeek)

    val backgroundColor = if (isSelected) {
        MaterialTheme.colors.primary
    } else {
        MaterialTheme.colors.surface
    }
    val contentColor =
        if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface

    Card(
        modifier = modifier
            .width(60.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() },
        backgroundColor = backgroundColor,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = day.toString(),
                color = contentColor,
                style = MaterialTheme.typography.subtitle1,
            )
            Text(
                text = weekDay,
                color = contentColor.copy(alpha = ContentAlpha.medium),
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

@Preview
@Composable
private fun HorizontalCalendarPreview() {
    AtomRemindersTheme {
        HorizontalCalendar(
            from = LocalDate.now().minusDays(PreviewPrevDays),
            until = LocalDate.now().plusDays(PreviewNextDays),
            onSelectDay = {},
        )
    }
}

@Preview()
@Composable
private fun CalendarDayPreview() {
    AtomRemindersTheme {
        CalendarDay(date = LocalDate.now(), isSelected = false, onClick = {})
    }
}

private const val PreviewPrevDays = 3L
private const val PreviewNextDays = 3L
