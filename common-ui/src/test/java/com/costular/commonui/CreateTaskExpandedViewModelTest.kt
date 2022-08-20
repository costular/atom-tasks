package com.costular.commonui

import app.cash.turbine.test
import com.costular.atomtasks.coretesting.MviViewModelTest
import com.costular.commonui.components.createtask.CreateTaskExpandedState
import com.costular.commonui.components.createtask.CreateTaskExpandedViewModel
import com.costular.commonui.components.createtask.CreateTaskResult
import com.costular.commonui.components.createtask.CreateTaskUiEvents
import com.google.common.truth.Truth.assertThat
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import org.junit.Before
import org.junit.Test

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

    @Test
    fun `should reset state when save succeed`() = testBlocking {
        val name = "name"
        val date = LocalDate.of(2021, 12, 24)
        val reminder = LocalTime.of(9, 0)

        sut.setName(name)
        sut.setDate(date)
        sut.setReminder(reminder)
        sut.requestSave()

        sut.state.test {
            assertThat(expectMostRecentItem()).isEqualTo(CreateTaskExpandedState.Empty)
        }
    }
}
