package com.costular.atomhabits.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomhabits.ui.util.DateTimeFormatters.dayOfWeekFormatter
import com.costular.atomhabits.ui.util.DateUtils
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun HorizontalCalendar(
    from: LocalDate,
    until: LocalDate,
    onSelectDay: (LocalDate) -> Unit,
    selectedDay: LocalDate = LocalDate.now(),
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState()
) {
    val coroutineScope = rememberCoroutineScope()
    val dates = DateUtils.datesBetween(from, until)
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dates) { item ->
            CalendarDay(
                date = item,
                isSelected = item == selectedDay,
                onClick = { onSelectDay(item) }
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
    modifier: Modifier = Modifier
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
        backgroundColor = backgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = day.toString(),
                color = contentColor,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                text = weekDay,
                color = contentColor.copy(alpha = ContentAlpha.medium),
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Preview
@Composable
private fun HorizontalCalendarPreview() {
    HorizontalCalendar(
        from = LocalDate.now().minusDays(3),
        until = LocalDate.now().plusDays(3),
        onSelectDay = {}
    )
}

@Preview()
@Composable
private fun CalendarDayPreview() {
    CalendarDay(date = LocalDate.now(), isSelected = false, onClick = {})
}