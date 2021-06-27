package com.costular.atomhabits.ui.features.habits.create

import app.cash.turbine.test
import com.costular.atomhabits.MviViewModelTest
import com.costular.atomhabits.domain.InvokeStarted
import com.costular.atomhabits.domain.InvokeSuccess
import com.costular.atomhabits.domain.interactor.CreateHabitInteractor
import com.costular.atomhabits.domain.model.Daily
import com.costular.atomhabits.domain.model.Weekly
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalTime
import kotlin.time.ExperimentalTime

@ExperimentalTime
class CreateHabitViewModelTest : MviViewModelTest() {

    private val createHabitInteractor: CreateHabitInteractor = mockk(relaxed = true)

    private lateinit var viewModel: CreateHabitViewModel

    @Before
    fun setUp() {
        viewModel = CreateHabitViewModel(dispatcherProvider, createHabitInteractor)
    }

    @Test
    fun `Test set name`() = testBlocking {
        // Given
        val newName = "Go workout!"

        // When
        viewModel.setName(newName)

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.name).isEqualTo(newName)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test set repetition`() = testBlocking {
        // Given
        val repetition = Weekly(DayOfWeek.FRIDAY)

        // When
        viewModel.setRepetition(repetition)

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.repetition).isEqualTo(repetition)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test set reminder enabled`() = testBlocking {
        // Given
        val reminderEnabled = true

        // When
        viewModel.setReminderEnabled(reminderEnabled)

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.reminderEnabled).isEqualTo(reminderEnabled)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test set reminder time`() = testBlocking {
        // Given
        val reminderTime = LocalTime.of(20, 30)

        // When
        viewModel.setReminderTime(reminderTime)

        // Then
        viewModel.state.test {
            val state = expectItem()
            Truth.assertThat(state.reminderTime).isEqualTo(reminderTime)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Test save successfully`() = testBlocking {
        // Given
        every { createHabitInteractor(any()) } returns flowOf(InvokeStarted, InvokeSuccess)
        val name = "whatever"
        val time = LocalTime.parse("22:20")
        val reminderEnabled = false
        val repetition = Daily

        // When
        viewModel.setName(name)
        viewModel.setRepetition(repetition)
        viewModel.setReminderEnabled(reminderEnabled)
        viewModel.setReminderTime(time)
        viewModel.save()

        // Then
        verify {
            createHabitInteractor(
                CreateHabitInteractor.Params(
                    name,
                    repetition,
                    reminderEnabled,
                    time
                )
            )
        }
    }

}