package com.costular.atomtasks.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DateTimeFormatters
import com.costular.atomtasks.ui.util.DateUtils
import com.costular.atomtasks.ui.util.DateUtils.timeAsText
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    time: LocalTime = LocalTime.now(),
    timeSuggestions: List<LocalTime> = emptyList(),
    onTimeChange: (LocalTime) -> Unit,
    paddingValues: PaddingValues = PaddingValues(AppTheme.dimens.contentMargin),
) {
    Column(
        modifier = modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (timeSuggestions.isNotEmpty()) {
            LazyRow(Modifier.fillMaxWidth()) {
                items(timeSuggestions) {
                    TimeSuggestionChip(
                        time = it,
                        onClick = {
                            onTimeChange(it)
                        },
                    )

                    Spacer(Modifier.width(AppTheme.dimens.spacingLarge))
                }
            }

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

@Composable
fun TimeSuggestionChip(
    time: LocalTime,
    onClick: () -> Unit,
) {
    Chip(onClick = onClick) {
        Text(
            text = timeAsText(time),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimePickerPreview() {
    AtomRemindersTheme {
        TimePicker(
            modifier = Modifier.fillMaxWidth(),
            onTimeChange = {},
            timeSuggestions = listOf(
                LocalTime.of(9, 0),
                LocalTime.of(11, 0),
                LocalTime.of(18, 0),
                LocalTime.of(21, 0),
            ),
        )
    }
}
