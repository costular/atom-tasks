package com.costular.atomtasks.screenshottesting.designsystem

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import app.cash.paparazzi.Paparazzi
import com.costular.atomtasks.screenshottesting.utils.FontSize
import com.costular.atomtasks.screenshottesting.utils.PaparazziFactory
import com.costular.atomtasks.screenshottesting.utils.Theme
import com.costular.atomtasks.screenshottesting.utils.asFloat
import com.costular.atomtasks.screenshottesting.utils.isDarkTheme
import com.costular.atomtasks.screenshottesting.utils.screenshot
import com.costular.commonui.components.RemovableChip
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class RemovableChipSnapshotTest {

    @TestParameter
    private lateinit var fontScale: FontSize

    @TestParameter
    private lateinit var themeMode: Theme

    @get:Rule
    val paparazzi: Paparazzi = PaparazziFactory.create()

    @Test
    fun removableChipUnselected() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat()
        ) {
            RemovableChip(
                title = "Reminder",
                icon = Icons.Default.CalendarToday,
                isSelected = false,
                onClick = {},
                onClear = {},
            )
        }
    }

    @Test
    fun removableChipSelected() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat()
        ) {
            RemovableChip(
                title = "Reminder",
                icon = Icons.Default.CalendarToday,
                isSelected = true,
                onClick = {},
                onClear = {},
            )
        }
    }
}
