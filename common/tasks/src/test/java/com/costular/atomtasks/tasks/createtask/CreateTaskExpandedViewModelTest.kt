package com.costular.atomtasks.tasks.createtask

import app.cash.turbine.test
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.tasks.usecase.AreExactRemindersAvailable
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class CreateTaskExpandedViewModelTest : MviViewModelTest() {

    lateinit var sut: CreateTaskExpandedViewModel

    private val areExactRemindersAvailable: AreExactRemindersAvailable = mockk()

    @Before
    fun setUp() {
        sut = CreateTaskExpandedViewModel(areExactRemindersAvailable)
    }

    @Test
    fun `should expose according state when land on screen`() = runTest {
        val expected = CreateTaskExpandedState.Empty

        sut.state.test {
            assertThat(expectMostRecentItem()).isEqualTo(expected)
        }
    }

    @Test
    fun `should expose task name when set name`() = runTest {
        val name = "🏃🏻‍♀️Running!"

        sut.setName(name)

        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.name).isEqualTo(name)
            assertThat(lastState.shouldShowSend).isTrue()
        }
    }

    @Test
    fun `should expose date when pass date`() = runTest {
        val date = LocalDate.of(2021, 12, 24)

        sut.setDate(date)

        sut.state.test {
            assertThat(expectMostRecentItem().date).isEqualTo(date)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose reminder when pass local time`() = runTest {
        val localTime = LocalTime.of(9, 0)

        sut.setReminder(localTime)

        sut.state.test {
            assertThat(expectMostRecentItem().reminder).isEqualTo(localTime)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should send save event with according data when request save`() = runTest {
        val name = "name"
        val date = LocalDate.of(2021, 12, 24)
        val reminder = LocalTime.of(9, 0)
        val recurrence = RecurrenceType.WEEKLY
        val expected = CreateTaskResult(name, date, reminder, recurrence)

        sut.setName(name)
        sut.setDate(date)
        sut.setReminder(reminder)
        sut.setRecurrence(RecurrenceType.WEEKLY)
        sut.requestSave()

        sut.uiEvents.test {
            assertThat(expectMostRecentItem()).isEqualTo(CreateTaskUiEvents.SaveTask(expected))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should reset state when save succeed`() = runTest {
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

    @Test
    fun `should show error when reminder is in the past`() = runTest {
        val date = LocalDate.now()
        val reminder = LocalTime.now().minusHours(2)

        sut.setDate(date)
        sut.setReminder(reminder)

        assertThat(sut.state.value.isReminderError).isTrue()
    }

    @Test
    fun `should show no error when reminder is in the future`() = runTest {
        val date = LocalDate.now().plusDays(1)
        val reminder = LocalTime.now().minusHours(2)

        sut.setDate(date)
        sut.setReminder(reminder)

        assertThat(sut.state.value.isReminderError).isFalse()
    }
}
