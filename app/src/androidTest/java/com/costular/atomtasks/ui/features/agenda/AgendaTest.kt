package com.costular.atomtasks.ui.features.agenda

import com.costular.atomtasks.R
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.domain.repository.TasksRepository
import com.costular.atomtasks.ui.base.AndroidTest
import com.costular.atomtasks.ui.base.getString
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.flowOf
import org.junit.Test
import java.time.LocalDate
import javax.inject.Inject

@HiltAndroidTest
class AgendaTest : AndroidTest() {

    @Inject
    lateinit var taskRepository: TasksRepository

    @Test
    fun shouldChangeSelectedDay_whenClickPrevDay() {
        agenda {
            goPrevDay()
            assertDayText(composeTestRule.getString(R.string.day_yesterday))
        }
    }

    @Test
    fun shouldChangeSelectedDay_whenClickNextDay() {
        agenda {
            goNextDay()
            assertDayText(composeTestRule.getString(R.string.day_tomorrow))
        }
    }

    @Test
    fun shouldChangeSelectedDayToToday_whenClickToday() {
        agenda {
            goNextDay()
            goToday()
            assertDayText(composeTestRule.getString(R.string.today))
        }
    }

    @Test
    fun shouldCreateATask_whenClickOnCreateNewTaskTypeAndThenSave() {
        val taskName = "whatever"

        agenda {
            openCreateTask {
                type(taskName)
                assertSaveIsDisplayed()
                save()
            }
        }

        agenda {
        }
    }

    @Test
    fun shouldShowTaskInList_whenLandOnScreen() {
        val taskName = "this is a test :D"
        val tasks = listOf(
            Task(
                1L,
                taskName,
                LocalDate.now(),
                null,
                true,
            )
        )
        coEvery { taskRepository.getTasks(LocalDate.now()) } returns flowOf(tasks)

        agenda {
            taskHasText(0, taskName)
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
            )
        )
        coEvery { taskRepository.getTasks(LocalDate.now()) } returns flowOf(tasks)

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
            )
        )
        coEvery { taskRepository.getTasks(LocalDate.now()) } returns flowOf(tasks)

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
            )
        )
        coEvery { taskRepository.getTasks(LocalDate.now()) } returns flowOf(tasks)

        agenda {
            toggleTask(taskName)
            coVerify { taskRepository.markTask(id, !isDone) }
        }
    }

}