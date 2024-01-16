package com.costular.designsystem.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.core.ui.date.Day
import com.costular.atomtasks.core.ui.date.asDay
import com.costular.atomtasks.core.ui.utils.ofLocalized
import com.costular.atomtasks.core.util.DateTimeFormatters.shortDayOfWeekFormatter
import com.costular.designsystem.theme.AtomTheme
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate

private const val DaysToShow = 365L

@Composable
fun WeekCalendar(
    modifier: Modifier = Modifier,
    selectedDay: Day = LocalDate.now().asDay(),
    startDate: LocalDate = remember(selectedDay) { selectedDay.date.minusDays(DaysToShow) },
    endDate: LocalDate = remember(selectedDay) { selectedDay.date.plusDays(DaysToShow) },
    onSelectDay: (LocalDate) -> Unit,
    weekCalendarState: WeekCalendarState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selectedDay.date,
    )
) {
    LaunchedEffect(selectedDay) {
        weekCalendarState.animateScrollToWeek(selectedDay.date)
    }

    com.kizitonwose.calendar.compose.WeekCalendar(
        modifier = modifier,
        state = weekCalendarState,
        dayContent = { weekDay ->
            CalendarDay(
                date = weekDay.date,
                isSelected = weekDay.date == selectedDay.date,
                isToday = LocalDate.now() == weekDay.date,
                onClick = {
                    onSelectDay(weekDay.date)
                },
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    )
}

@Composable
private fun CalendarDay(
    date: LocalDate,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val day = date.dayOfMonth
    val weekDay = shortDayOfWeekFormatter.ofLocalized(date.dayOfWeek)

    val cardColors = if (isSelected) {
        CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    } else {
        CardDefaults.outlinedCardColors()
    }

    OutlinedCard(
        colors = cardColors,
        modifier = modifier
            .height(70.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = weekDay,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = day.toString(),
                style = MaterialTheme.typography.titleMedium
            )

            if (isToday) {
                IndicatorToday(
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.60f)
                )
            }
        }
    }
}

@Composable
fun IndicatorToday(
    modifier: Modifier = Modifier,
    color: Color,
) {
    Canvas(
        modifier = modifier.size(4.dp),
        onDraw = {
            drawCircle(
                color = color,
            )
        }
    )
}

@Preview
@Composable
private fun HorizontalCalendarPreview() {
    AtomTheme {
        WeekCalendar(
            selectedDay = LocalDate.now().asDay(),
            onSelectDay = {}
        )
    }
}
