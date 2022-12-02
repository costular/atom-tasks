package com.costular.atomtasks.coreui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.coreui.R
import com.costular.core.util.DateTimeFormatters
import com.costular.core.util.DateTimeFormatters.timeFormatter
import java.time.LocalDate
import java.time.LocalTime

object DateUtils {

    @Composable
    fun dayAsText(day: LocalDate): String {
        return when (day) {
            LocalDate.now() -> stringResource(R.string.day_today)
            LocalDate.now().minusDays(1) -> stringResource(R.string.day_yesterday)
            LocalDate.now().plusDays(1) -> stringResource(R.string.day_tomorrow)
            else -> DateTimeFormatters.dateFormatter.format(day)
        }
    }

    @Composable
    fun timeAsText(time: LocalTime): String =
        timeFormatter.format(time)
}
