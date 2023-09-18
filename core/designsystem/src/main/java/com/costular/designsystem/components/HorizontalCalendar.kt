package com.costular.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.coreui.date.Day
import com.costular.atomtasks.coreui.date.asDay
import com.costular.core.util.DateTimeFormatters.shortDayOfWeekFormatter
import com.costular.core.util.WeekUtil
import com.costular.designsystem.theme.AtomTheme
import java.time.LocalDate

@Composable
fun HorizontalCalendar(
    selectedDay: Day = LocalDate.now().asDay(),
    weekDays: List<LocalDate> = remember(selectedDay) { WeekUtil.getWeekDays(selectedDay.date) },
    onSelectDay: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        weekDays.forEach { date ->
            CalendarDay(
                date = date,
                isSelected = date == selectedDay.date,
                onClick = { onSelectDay(date) }
            )
        }
    }
}

@Composable
private fun RowScope.CalendarDay(
    date: LocalDate,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val day = date.dayOfMonth
    val weekDay = shortDayOfWeekFormatter.format(date.dayOfWeek)

    val cardColors = if (isSelected) {
        CardDefaults.outlinedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    } else {
        CardDefaults.outlinedCardColors()
    }

    OutlinedCard(
        colors = cardColors,
        modifier = modifier
            .weight(1f)
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
        }
    }
}

@Preview
@Composable
private fun HorizontalCalendarPreview() {
    AtomTheme {
        HorizontalCalendar(
            selectedDay = LocalDate.now().asDay(),
            onSelectDay = {}
        )
    }
}
