package com.costular.atomtasks.screenshots.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.costular.atomtasks.domain.model.Reminder
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.screenshots.ScreenshotTest
import com.costular.atomtasks.ui.base.SnapshotTest
import com.costular.atomtasks.ui.components.TaskList
import com.google.accompanist.insets.ui.Scaffold
import java.time.LocalDate
import java.time.LocalTime
import org.junit.Test

@ScreenshotTest
class TaskListSnapshotTest : SnapshotTest() {

    @Test
    fun emptyTaskList() {
        runScreenshotTest {
            GeneratedTaskList(tasks = emptyList())
        }
    }

    @Test
    fun taskList() {
        runScreenshotTest {
            GeneratedTaskList(
                tasks = listOf(
                    Task(
                        0L,
                        "Task 1",
                        LocalDate.now(),
                        null,
                        true,
                    ),
                    Task(
                        id = 0L,
                        name = "Task 2",
                        createdAt = LocalDate.now(),
                        reminder = Reminder(
                            id = 1L,
                            time = LocalTime.of(9, 0),
                            isEnabled = true,
                            date = null,
                        ),
                        isDone = false,
                    ),
                    Task(
                        id = 0L,
                        name = "Task 3",
                        createdAt = LocalDate.now(),
                        reminder = null,
                        isDone = false,
                    ),
                ),
            )
        }
    }

    @Composable
    private fun GeneratedTaskList(tasks: List<Task>) {
        Scaffold {
            TaskList(
                tasks = tasks,
                onClick = {},
                onMarkTask = { _, _ -> },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}
