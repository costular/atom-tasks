package com.costular.atomhabits.ui.components

import androidx.compose.runtime.Composable
import java.time.DayOfWeek
import java.time.YearMonth

@Composable
fun MonthCalendar(
    month: YearMonth,

) {

}

@Composable
private fun MonthCalendarWeek(
    yearMonth: YearMonth = YearMonth.now(),
    startDay: DayOfWeek = DayOfWeek.MONDAY,
    content: @Composable () -> Unit
) {
    
}