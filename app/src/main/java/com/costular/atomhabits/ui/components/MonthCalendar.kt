package com.costular.atomhabits.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.YearMonth
import java.util.*

/**
 * Thanks to Halil Ozercan for inspiration (https://gist.github.com/halilozercan/32c3270e1f4dc79449b4d0f28e4e30a6)
 */

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CalendarView(
    modifier: Modifier = Modifier,
    selectedDay: CalendarDay = CalendarDay.create(),
    onSelectedDayChange: (CalendarDay) -> Unit = {},
    visibleMonth: CalendarDay = CalendarDay.create(),
    onVisibleMonthChange: (CalendarDay) -> Unit = {},
    today: CalendarDay = CalendarDay.create(),
    events: Set<CalendarDay> = emptySet()
) {
    val currentMonthCalendar = remember(visibleMonth) { visibleMonth.toCalendar() }
    Column(modifier = modifier.background(MaterialTheme.colors.surface)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Text(
                SimpleDateFormat("MMMM YYYY").format(currentMonthCalendar.time),
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                style = MaterialTheme.typography.h6
            )
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {
                onVisibleMonthChange(visibleMonth.monthBefore)
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous month"
                )
            }
            IconButton(onClick = {
                onVisibleMonthChange(visibleMonth.monthAfter)
            }) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next month",
                )
            }
        }

        SlideInOutLayout(visibleMonth.year * 12 + visibleMonth.month) { index ->
            CalendarPage(
                month = CalendarDay(day = 1, month = index % 12, year = index / 12),
                onDayClick = {
                    onVisibleMonthChange(CalendarDay(day = 1, month = it.month, year = it.year))
                    onSelectedDayChange(it)
                },
                selectedDay = selectedDay,
                today = today,
                events = events
            )
        }
    }
}

@Composable
fun CalendarPage(
    month: CalendarDay,
    onDayClick: (CalendarDay) -> Unit,
    selectedDay: CalendarDay,
    today: CalendarDay,
    events: Set<CalendarDay>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val startDayCalendar = month.toCalendar().firstFirstDayOfWeekBeforeThisMonth()
        val iteratorCalendar = startDayCalendar.clone() as Calendar

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            repeat(7) {
                Text(
                    text = (SimpleDateFormat("EEE").format(iteratorCalendar.time).toUpperCase()),
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    modifier = Modifier.weight(1f)
                )
                iteratorCalendar.add(Calendar.DATE, 1)
            }
            iteratorCalendar.add(Calendar.DATE, -7)
        }

        repeat(6) { // 6 weeks
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 2.dp)
            ) {
                repeat(7) { // 7 days a week
                    val calendarDay = iteratorCalendar.toCalendarDay()
                    CalendarDayView(
                        number = calendarDay.day,
                        onClick = { onDayClick(calendarDay) },
                        isSelected = calendarDay == selectedDay,
                        isToday = calendarDay == today,
                        isEventful = events.contains(calendarDay),
                        isInCurrentMonth = month.month == calendarDay.month && month.year == calendarDay.year
                    )
                    iteratorCalendar.add(Calendar.DATE, 1)
                }
            }
        }
    }
}

@Composable
fun RowScope.CalendarDayView(
    number: Int,
    onClick: () -> Unit,
    isSelected: Boolean,
    isToday: Boolean,
    isEventful: Boolean,
    isInCurrentMonth: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .weight(1f)
            .padding(horizontal = 2.dp)
            .alpha(if (isInCurrentMonth) 1f else 0.4f)
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface)
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$number",
            fontSize = 14.sp,
            lineHeight = 16.71.sp,
            fontWeight = if (isToday) FontWeight.W700 else FontWeight.Normal
        )
        Spacer(Modifier.height(6.dp))
        Canvas(
            Modifier
                .size(6.dp)
                .alpha(if (isEventful) 1f else 0f)
        ) {

        }
    }
}

fun Calendar.firstFirstDayOfWeekBeforeThisMonth(): Calendar {
    return (this.clone() as Calendar).apply {
        set(Calendar.DAY_OF_MONTH, 1)
        while (get(Calendar.DAY_OF_WEEK) != firstDayOfWeek) {
            add(Calendar.DATE, -1)
        }
    }
}

data class CalendarDay(
    val day: Int,
    val month: Int,
    val year: Int
) {
    companion object {
        fun create(): CalendarDay = with(Calendar.getInstance()) {
            CalendarDay(
                day = get(Calendar.DAY_OF_MONTH),
                month = get(Calendar.MONTH),
                year = get(Calendar.YEAR)
            )
        }
    }
}

val CalendarDay.monthBefore: CalendarDay
    get() = toCalendar().apply { add(Calendar.MONTH, -1) }.toCalendarDay()

val CalendarDay.monthAfter: CalendarDay
    get() = toCalendar().apply { add(Calendar.MONTH, 1) }.toCalendarDay()

fun Calendar.toCalendarDay(): CalendarDay = CalendarDay(
    day = get(Calendar.DAY_OF_MONTH),
    month = get(Calendar.MONTH),
    year = get(Calendar.YEAR),
)

fun CalendarDay.toCalendar(): Calendar = Calendar.getInstance().apply {
    set(year, month, day)
    firstDayOfWeek = Calendar.SUNDAY
}