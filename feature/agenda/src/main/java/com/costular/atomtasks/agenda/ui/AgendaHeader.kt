package com.costular.atomtasks.agenda.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.Today
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.core.ui.R
import com.costular.atomtasks.core.ui.date.Day
import com.costular.atomtasks.core.ui.date.asDay
import com.costular.atomtasks.core.ui.utils.DateUtils
import com.costular.designsystem.components.DatePicker
import com.costular.designsystem.components.ScreenHeader
import com.costular.designsystem.components.WeekCalendar
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.costular.designsystem.util.supportWideScreen
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import java.time.LocalDate
import kotlinx.coroutines.launch

@Suppress("LongMethod")
@Composable
internal fun AgendaHeader(
    modifier: Modifier = Modifier,
    selectedDay: Day,
    onSelectDate: (LocalDate) -> Unit,
    onSelectToday: () -> Unit,
    canExpand: Boolean,
    isExpanded: Boolean,
    onToggleExpandCollapse: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val startDate = remember(selectedDay) { selectedDay.date.minusDays(365) }
    val endDate = remember(selectedDay) { selectedDay.date.plusDays(365) }

    val weekCalendarState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstVisibleWeekDate = selectedDay.date,
    )

    val shadowElevation = if (isExpanded) {
        6.dp
    } else {
        2.dp
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        shadowElevation = shadowElevation,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val selectedDayText = DateUtils.dayAsText(selectedDay.date)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable(enabled = canExpand, onClick = onToggleExpandCollapse),
                ) {
                    ScreenHeader(
                        text = selectedDayText,
                        modifier = Modifier
                            .testTag(TestTagHeader)
                            .padding(
                                top = AppTheme.dimens.spacingLarge,
                                bottom = AppTheme.dimens.spacingLarge,
                                start = AppTheme.dimens.spacingLarge,
                            ),
                    )

                    val degrees by animateFloatAsState(
                        targetValue = if (isExpanded) 180f else 0f,
                        animationSpec = tween(
                            durationMillis = 200,
                            easing = FastOutSlowInEasing,
                        ),
                        label = "Header collapsable arrow",
                    )

                    if (canExpand) {
                        IconButton(onClick = { onToggleExpandCollapse() }) {
                            Icon(
                                imageVector = Icons.Default.ExpandMore,
                                contentDescription = null,
                                modifier = Modifier.rotate(degrees),
                            )
                        }
                    }
                }

                IconButton(
                    onClick = {
                        coroutineScope.launch {
                            weekCalendarState.animateScrollToWeek(LocalDate.now())
                        }
                        onSelectToday()
                    },
                    modifier = Modifier
                        .padding(end = AppTheme.dimens.spacingLarge)
                        .width(40.dp)
                        .height(40.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Today,
                        contentDescription = stringResource(R.string.today),
                    )
                }
            }

            AnimatedContent(
                targetState = isExpanded && canExpand,
                label = "Header calendar",
            ) { isCollapsed ->
                if (isCollapsed) {
                    ExpandedCalendar(selectedDay, onSelectDate)
                } else {
                    CollapsedCalendar(
                        selectedDay,
                        onSelectDate,
                        weekCalendarState,
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpandedCalendar(
    selectedDay: Day,
    onSelectDate: (LocalDate) -> Unit,
) {
    DatePicker(
        modifier = Modifier
            .supportWideScreen(480.dp)
            .padding(horizontal = AppTheme.dimens.contentMargin)
            .padding(bottom = AppTheme.dimens.spacingMedium),
        selectedDay = selectedDay.date,
        onDateSelected = onSelectDate,
    )
}

@Composable
private fun CollapsedCalendar(
    selectedDay: Day,
    onSelectDate: (LocalDate) -> Unit,
    weekCalendarState: WeekCalendarState = rememberWeekCalendarState(),
) {
    WeekCalendar(
        modifier = Modifier
            .supportWideScreen()
            .padding(
                start = AppTheme.dimens.spacingLarge,
                end = AppTheme.dimens.spacingLarge,
                bottom = AppTheme.dimens.spacingLarge,
            ),
        selectedDay = selectedDay,
        onSelectDay = onSelectDate,
        weekCalendarState = weekCalendarState,
    )
}

@Preview
@Composable
private fun HeaderCollapsedPreview() {
    AtomTheme {
        AgendaHeader(
            selectedDay = LocalDate.now().asDay(),
            onSelectDate = {},
            onSelectToday = {},
            canExpand = true,
            isExpanded = false,
            onToggleExpandCollapse = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun HeaderExpandedPreview() {
    AtomTheme {
        AgendaHeader(
            selectedDay = LocalDate.now().asDay(),
            onSelectDate = {},
            onSelectToday = {},
            canExpand = true,
            isExpanded = true,
            onToggleExpandCollapse = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
