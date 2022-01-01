package com.costular.atomreminders.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.costular.atomreminders.ui.theme.AppTheme
import com.costular.atomreminders.ui.util.DateTimeFormatters
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    time: LocalTime = LocalTime.now(),
    onTimeChange: (LocalTime) -> Unit,
    showTimeZone: Boolean = true,
    showTime: Boolean = true,
    paddingValues: PaddingValues = PaddingValues(AppTheme.dimens.contentMargin),
) {
    Column(
        modifier = modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val locale = Locale.getDefault()
        val zone = ZoneId.systemDefault()
        val timezone = zone.getDisplayName(TextStyle.SHORT_STANDALONE, locale)
        val timeFormatted = remember(time) { DateTimeFormatters.timeFormatter.format(time) }

        if (showTimeZone) {
            Text(
                timezone,
                style = MaterialTheme.typography.caption
            )
            Spacer(Modifier.height(AppTheme.dimens.spacingMedium))
        }

        if (showTime) {
            Text(
                timeFormatted,
                style = MaterialTheme.typography.h6
            )
            Spacer(Modifier.height(AppTheme.dimens.spacingLarge))
        }

        HoursNumberPicker(
            dividersColor = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxWidth(),
            value = FullHours(time.hour, time.minute),
            onValueChange = {
                onTimeChange(LocalTime.of(it.hours, it.minutes))
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimePickerPreview() {
    TimePicker(
        modifier = Modifier.fillMaxWidth(),
        onTimeChange = {}
    )
}