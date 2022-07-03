package com.costular.atomtasks.ui.features.agenda

import com.costular.atomtasks.R
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.ui.base.AndroidTest
import com.costular.atomtasks.ui.base.getString
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import org.junit.Test

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
        val task = Task(
            id = 1L,
            name = "this is a test :D",
            createdAt = LocalDate.now(),
            reminder = null,
            isDone = true,
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
            Task(
                1L,
                taskName,
                LocalDate.now(),
                null,
                isDone,
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
            Task(
                1L,
                taskName,
                LocalDate.now(),
                null,
                isDone,
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
            Task(
                id,
                taskName,
                LocalDate.now(),
                null,
                isDone,
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

    private fun givenAgenda(state: AgendaState = AgendaState.Empty) {
        composeTestRule.setContent {
            AgendaScreen(
                state = state,
                onSelectDate = {},
                actionDelete = {},
                dismissDelete = {},
                dismissTaskAction = {},
                onMarkTask = toggleTask,
                deleteTask = {},
                onCreateTask = {},
                openTaskAction = {},
            )
        }
    }
}
