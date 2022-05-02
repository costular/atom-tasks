package com.costular.atomtasks.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.ui.theme.AppTheme
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.DayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun DatePicker(
    modifier: Modifier = Modifier,
    currentDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
) {
    val calendarState = rememberSelectableCalendarState(
        initialSelection = listOf(currentDate),
        confirmSelectionChange = { newDates: List<LocalDate> ->
            newDates.firstOrNull()?.let {
                onDateSelected(it)
                true
            } ?: run {
                onDateSelected(currentDate)
                false
            }
        },
    )

    SelectableCalendar(
        modifier = modifier,
        today = currentDate,
        calendarState = calendarState,
        horizontalSwipeEnabled = true,
        monthHeader = { HeaderMonth(it) },
        weekHeader = { WeekHeader(it) },
        dayContent = { CalendarDay(it) },
    )
}

@Composable
private fun HeaderMonth(
    monthState: MonthState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = AppTheme.dimens.spacingMedium),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            modifier = Modifier.testTag("Decrement"),
            onClick = { monthState.currentMonth = monthState.currentMonth.minusMonths(1) },
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowLeft,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Previous",
            )
        }
        Text(
            text = monthState.currentMonth.month.name.lowercase()
                .replaceFirstChar { it.titlecase() },
            style = MaterialTheme.typography.h6,
        )
        Spacer(modifier = Modifier.width(AppTheme.dimens.spacingMedium))
        Text(text = monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h6)
        IconButton(
            modifier = Modifier.testTag("Increment"),
            onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) },
        ) {
            Image(
                imageVector = Icons.Default.KeyboardArrowRight,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Next",
            )
        }
    }
}

@Composable
private fun WeekHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        daysOfWeek.forEach { dayOfWeek ->
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    textAlign = TextAlign.Center,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ROOT),
                    modifier = modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}

@Composable
private fun <T : SelectionState> CalendarDay(
    state: DayState<T>,
    modifier: Modifier = Modifier,
) {
    val date = state.date
    val selectionState = state.selectionState

    val isSelected = selectionState.isDateSelected(date)
    val isToday = date == LocalDate.now()

    val backgroundColor =
        if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.background
    val contentColor = contentColorFor(backgroundColor)
    val contentAlpha = if (state.isFromCurrentMonth) ContentAlpha.high else ContentAlpha.disabled

    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
            .clip(MaterialTheme.shapes.small)
            .background(backgroundColor)
            .clickable {
                selectionState.onDateSelected(date)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold),
            color = contentColor.copy(alpha = contentAlpha),
        )

        if (isToday) {
            val todayBackgroundColor =
                if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.primary
            Spacer(Modifier.height(AppTheme.dimens.spacingSmall))

            Box(
                modifier = Modifier
                    .size(4.dp)
                    .clip(CircleShape)
                    .background(todayBackgroundColor),
            )
        }
    }
}
