package com.costular.atomtasks.screenshottesting.designsystem

import app.cash.paparazzi.Paparazzi
import com.costular.atomtasks.core.ui.date.asDay
import com.costular.atomtasks.screenshottesting.utils.FontSize
import com.costular.atomtasks.screenshottesting.utils.PaparazziFactory
import com.costular.atomtasks.screenshottesting.utils.Theme
import com.costular.atomtasks.screenshottesting.utils.asFloat
import com.costular.atomtasks.screenshottesting.utils.isDarkTheme
import com.costular.atomtasks.screenshottesting.utils.screenshot
import com.costular.designsystem.components.WeekCalendar
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import java.time.LocalDate
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class HorizontalCalendarSnapshotTest {

    @TestParameter
    private lateinit var fontScale: FontSize

    @TestParameter
    private lateinit var themeMode: Theme

    @get:Rule
    val paparazzi: Paparazzi = PaparazziFactory.create()

    @Test
    fun horizontalCalendarTest() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat()
        ) {
            WeekCalendar(
                onSelectDay = {},
                weekDays = WeekDays,
                selectedDay = LocalDate.of(2023, 9, 1).asDay(),
            )
        }
    }

    private companion object {
        val WeekDays = listOf(
            LocalDate.of(2023, 8, 28),
            LocalDate.of(2023, 8, 29),
            LocalDate.of(2023, 8, 30),
            LocalDate.of(2023, 8, 31),
            LocalDate.of(2023, 9, 1),
            LocalDate.of(2023, 9, 2),
            LocalDate.of(2023, 9, 3)
        )
    }
}
