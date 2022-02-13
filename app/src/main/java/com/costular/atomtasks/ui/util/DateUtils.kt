package com.costular.atomtasks.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.R
import java.time.LocalDate

object DateUtils {

    fun datesBetween(start: LocalDate, end: LocalDate): List<LocalDate> {
        var localStart = start
        val dates = mutableListOf<LocalDate>()
        while (!localStart.isAfter(end)) {
            dates.add(localStart)
            localStart = localStart.plusDays(1)
        }
        return dates
    }

    @Composable
    fun dayAsText(day: LocalDate): String {
        return when (day) {
            LocalDate.now() -> stringResource(R.string.day_today)
            LocalDate.now().minusDays(1) -> stringResource(R.string.day_yesterday)
            LocalDate.now().plusDays(1) -> stringResource(R.string.day_tomorrow)
            else -> DateTimeFormatters.dateFormatter.format(day)
        }
    }
}
