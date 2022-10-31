package com.costular.atomtasks.screenshottesting.designsystem

import app.cash.paparazzi.Paparazzi
import com.costular.atomtasks.tasks.Reminder
import com.costular.atomtasks.screenshottesting.utils.FontSize
import com.costular.atomtasks.screenshottesting.utils.PaparazziFactory
import com.costular.atomtasks.screenshottesting.utils.Theme
import com.costular.atomtasks.screenshottesting.utils.asFloat
import com.costular.atomtasks.screenshottesting.utils.isDarkTheme
import com.costular.atomtasks.screenshottesting.utils.screenshot
import com.costular.atomtasks.tasks.TaskCard
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class TaskSnapshotTest {

    @TestParameter
    private lateinit var fontScale: FontSize

    @TestParameter
    private lateinit var themeMode: Theme

    @get:Rule
    val paparazzi: Paparazzi = PaparazziFactory.create()

    @Test
    fun taskTest() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat(),
        ) {
            TaskCard(
                title = "This is a test!",
                isFinished = false,
                reminder = null,
                onMark = {},
                onOpen = {},
            )
        }
    }

    @Test
    fun taskDoneTest() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat(),
        ) {
            TaskCard(
                title = "this is a finished test!",
                isFinished = true,
                reminder = null,
                onMark = {},
                onOpen = {},
            )
        }
    }

    @Test
    fun taskWithReminder() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat(),
        ) {
            TaskCard(
                title = "This is a task with reminder",
                isFinished = false,
                reminder = Reminder(
                    0L,
                    LocalTime.of(9, 0),
                    true,
                    LocalDate.now(),
                ),
                onMark = { },
                onOpen = { },
            )
        }
    }

    @Test
    fun taskFinishedWithReminder() {
        paparazzi.screenshot(
            isDarkTheme = themeMode.isDarkTheme(),
            fontScale = fontScale.asFloat(),
        ) {
            TaskCard(
                title = "This is a task with reminder",
                isFinished = true,
                reminder = Reminder(
                    0L,
                    LocalTime.of(9, 0),
                    true,
                    LocalDate.now(),
                ),
                onMark = { },
                onOpen = { },
            )
        }
    }
}
