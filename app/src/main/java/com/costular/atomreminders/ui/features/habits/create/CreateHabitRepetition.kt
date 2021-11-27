package com.costular.atomreminders.ui.features.habits.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomreminders.domain.model.Daily
import com.costular.atomreminders.domain.model.Repetition
import com.costular.atomreminders.domain.model.Weekly
import com.costular.atomreminders.ui.components.ScreenHeader
import com.costular.atomreminders.ui.util.DateTimeFormatters.shortDayOfWeekFormatter
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun CreateHabitRepetition(
    createHabitState: CreateHabitState,
    onRepetitionSelected: (Repetition) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            text = stringResource(R.string.create_habit_repetition_header),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Selector(
            selectedRepetition = createHabitState.repetition,
            onSelected = { onRepetitionSelected(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
private fun Selector(
    selectedRepetition: Repetition,
    onSelected: (Repetition) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedColor = MaterialTheme.colors.primary
    val unselectedColor = MaterialTheme.colors.surface

    Column {
        Row(
            modifier = modifier.clip(MaterialTheme.shapes.small)
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .clickable { onSelected(Daily) }
                    .background(if (selectedRepetition is Daily) selectedColor else unselectedColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.repetition_everyday),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .clickable { onSelected(Weekly(LocalDate.now().dayOfWeek)) }
                    .background(if (selectedRepetition is Weekly) selectedColor else unselectedColor),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.repetition_weekly),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }

        if (selectedRepetition is Weekly) {
            WeekDaySelector(
                dayOfWeekSelected = selectedRepetition.dayOfWeek,
                onSelectedDayOfWeek = { onSelected(Weekly(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun WeekDaySelector(
    dayOfWeekSelected: DayOfWeek,
    onSelectedDayOfWeek: (DayOfWeek) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
        DayOfWeek.values().forEach { dayOfWeek ->
            WeekDay(
                dayOfWeek = dayOfWeek,
                isSelected = dayOfWeekSelected == dayOfWeek,
                onClick = { onSelectedDayOfWeek(dayOfWeek) },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
    }
}

@Composable
private fun WeekDay(
    dayOfWeek: DayOfWeek,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface

    Surface(modifier = modifier.clickable { onClick() }, color = backgroundColor) {
        Box(contentAlignment = Alignment.Center) {
            Text(shortDayOfWeekFormatter.format(dayOfWeek))
        }
    }
}

@Preview
@Composable
private fun WeekDaySelectorPreview() {
    WeekDaySelector(DayOfWeek.MONDAY, onSelectedDayOfWeek = {}, modifier = Modifier.fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
private fun SelectorPreview() {
    Selector(Daily, onSelected = {})
}