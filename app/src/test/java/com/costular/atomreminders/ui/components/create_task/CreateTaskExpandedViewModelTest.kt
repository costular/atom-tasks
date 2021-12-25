package com.costular.atomreminders.ui.components.create_task

import app.cash.turbine.test
import com.costular.atomreminders.MviViewModelTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CreateTaskExpandedViewModelTest : MviViewModelTest() {

    lateinit var sut: CreateTaskExpandedViewModel

    @Before
    fun setUp() {
        sut = CreateTaskExpandedViewModel()
    }

    @Test
    fun `should expose according state when land on screen`() = testBlocking {
        val expected = CreateTaskExpandedState.Empty

        sut.state.test {
            assertThat(expectMostRecentItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `should expose task name when set name`() = testBlocking {
        val name = "🏃🏻‍♀️Running!"

        sut.setName(name)

        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.name).isEqualTo(name)
            assertThat(lastState.shouldShowSend).isTrue()
        }
    }

    @Test
    fun `should expose date when pass date`() = testBlocking {
        val date = LocalDate.of(2021, 12, 24)

        sut.setDate(date)

        sut.state.test {
            assertThat(expectMostRecentItem().date).isEqualTo(date)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose reminder when pass local time`() = testBlocking {
        val localTime = LocalTime.of(9, 0)

        sut.setReminder(localTime)

        sut.state.test {
            assertThat(expectMostRecentItem().reminder).isEqualTo(localTime)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose date selection when select date`() = testBlocking {
        val dataSelection = TaskDataSelection.Date

        sut.selectTaskData(dataSelection)

        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.taskDataSelection)
                .isInstanceOf(dataSelection::class.java)
            assertThat(lastState.shouldShowDateSelection).isTrue()
        }
    }

    @Test
    fun `should expose reminder selection when select reminder`() = testBlocking {
        val reminderSelection = TaskDataSelection.Reminder

        sut.selectTaskData(reminderSelection)

        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.taskDataSelection)
                .isInstanceOf(reminderSelection::class.java)
            assertThat(lastState.shouldShowReminderSelection).isTrue()
        }
    }

    @Test
    fun `should expose none data selection when select data selection already selected`() =
        testBlocking {
            val dataSelection = TaskDataSelection.Date

            sut.selectTaskData(dataSelection)
            sut.selectTaskData(dataSelection)

            sut.state.test {
                assertThat(expectMostRecentItem().taskDataSelection)
                    .isInstanceOf(TaskDataSelection.None::class.java)
            }
        }

    @Test
    fun `should send save event with according data when request save`() = testBlocking {
        val name = "name"
        val date = LocalDate.of(2021, 12, 24)
        val reminder = LocalTime.of(9, 0)
        val expected = CreateTaskResult(name, date, reminder)

        sut.setName(name)
        sut.setDate(date)
        sut.setReminder(reminder)
        sut.requestSave()

        sut.uiEvents.test {
            assertThat(expectMostRecentItem()).isEqualTo(CreateTaskUiEvents.SaveTask(expected))
            cancelAndIgnoreRemainingEvents()
        }
    }

}