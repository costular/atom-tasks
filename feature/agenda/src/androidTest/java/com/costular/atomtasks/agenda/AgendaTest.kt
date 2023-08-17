package com.costular.atomtasks.agenda

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.ui.unit.DpSize
import com.costular.atomtasks.core.testing.ui.AndroidTest
import com.costular.atomtasks.core.testing.ui.getString
import com.costular.core.Async
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import org.junit.Test
import com.costular.atomtasks.core.ui.R

@HiltAndroidTest
class AgendaTest : AndroidTest() {

    private val toggleTask: (Long, Boolean) -> Unit = mockk(relaxed = true)

    @Test
    fun shouldShowYesterdayHeader_whenSelectedDayIsYesterday() {
        givenAgenda(
            state = AgendaState(
                selectedDay = LocalDate.now().minusDays(1),
            ),
        )

        agenda {
            assertDayText(composeTestRule.getString(R.string.day_yesterday))
        }
    }

    @Test
    fun shouldShowTomorrowHeader_whenSelectedDayIsTomorrow() {
        givenAgenda(
            state = AgendaState(
                selectedDay = LocalDate.now().plusDays(1),
            ),
        )

        agenda {
            goNextDay()
            assertDayText(composeTestRule.getString(R.string.day_tomorrow))
        }
    }

    @Test
    fun shouldShowTodayHeader_whenSelectedDayIsToday() {
        givenAgenda()

        agenda {
            assertDayText(composeTestRule.getString(R.string.today))
        }
    }

    @Test
    fun shouldShowTaskInList_whenLandOnScreen() {
        val task = com.costular.atomtasks.tasks.Task(
            id = 1L,
            name = "this is a test :D",
            createdAt = LocalDate.now(),
            reminder = null,
            isDone = true,
            day = LocalDate.now(),
        )

        givenAgenda(
            state = AgendaState(
                tasks = Async.Success(
                    listOf(task),
                ),
            ),
        )

        agenda {
            taskHasText(0, task.name)
        }
    }

    @Test
    fun shouldShowTaskMarkedAsDone_whenLandOnScreen() {
        val taskName = "this is a test :D"
        val isDone = true

        val tasks = listOf(
            com.costular.atomtasks.tasks.Task(
                id = 1L,
                name = taskName,
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = null,
                isDone = isDone,
            ),
        )

        givenAgenda(
            state = AgendaState(
                tasks = Async.Success(tasks),
            ),
        )

        agenda {
            taskIsDone(taskName, isDone)
        }
    }

    @Test
    fun shouldShowTaskMarkedAsNotDone_whenLandOnScreen() {
        val taskName = "this is a test :D"
        val isDone = false

        val tasks = listOf(
            com.costular.atomtasks.tasks.Task(
                id = 1L,
                name = taskName,
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = null,
                isDone = isDone,
            ),
        )

        givenAgenda(
            state = AgendaState(
                tasks = Async.Success(tasks),
            ),
        )

        agenda {
            taskIsDone(taskName, isDone)
        }
    }

    @Test
    fun shouldToggleTaskStatus_whenClickOnTaskCheckbox() {
        val id = 1L
        val taskName = "this is a test :D"
        val isDone = false

        val tasks = listOf(
            com.costular.atomtasks.tasks.Task(
                id = id,
                name = taskName,
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = null,
                isDone = isDone,
            ),
        )

        givenAgenda(
            state = AgendaState(
                tasks = Async.Success(tasks),
            ),
        )

        agenda {
            toggleTask(taskName)
            verify { toggleTask(id, !isDone) }
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    private fun givenAgenda(state: AgendaState = AgendaState.Empty) {
        composeTestRule.setContent {
            AgendaScreen(
                state = state,
                onMarkTask = toggleTask,
                windowSizeClass = WindowSizeClass.calculateFromSize(DpSize.Unspecified),
                onSelectDate = {},
                onSelectToday = {},
                actionDelete = {},
                dismissTaskAction = {},
                deleteTask = {},
                dismissDelete = {},
                openTaskAction = {},
                onEditAction = {},
                onToggleExpandCollapse = {},
            )
        }
    }
}
