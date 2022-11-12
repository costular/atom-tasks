package com.costular.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chargemap.compose.numberpicker.FullHours
import com.chargemap.compose.numberpicker.HoursNumberPicker
import com.costular.atomtasks.coreui.utils.DateUtils.timeAsText
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomRemindersTheme
import java.time.LocalTime

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
            dividersColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            value = FullHours(time.hour, time.minute),
            onValueChange = {
                onTimeChange(LocalTime.of(it.hours, it.minutes))
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSuggestionChip(
    time: LocalTime,
    onClick: () -> Unit,
) {
    AssistChip(
        onClick = onClick,
        label = {
            Text(
                text = timeAsText(time),
            )
        },
    )
}

@Suppress("MagicNumber")
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
