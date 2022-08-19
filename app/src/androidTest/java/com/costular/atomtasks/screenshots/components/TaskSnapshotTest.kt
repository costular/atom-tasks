package com.costular.atomtasks.screenshots.components

import com.costular.atomtasks.domain.model.Reminder
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.TaskCard
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Test

@ScreenshotTest
class TaskSnapshotTest : SnapshotTest() {

    @Test
    fun taskTest() {
        runScreenshotTest {
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
        runScreenshotTest {
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
        runScreenshotTest {
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
        runScreenshotTest {
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
