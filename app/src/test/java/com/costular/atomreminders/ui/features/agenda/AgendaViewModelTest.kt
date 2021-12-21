package com.costular.atomreminders.ui.features.agenda

import app.cash.turbine.test
import com.costular.atomreminders.MviViewModelTest
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.interactor.GetTasksInteractor
import com.costular.atomreminders.domain.interactor.UpdateTaskInteractor
import com.costular.atomreminders.domain.model.Task
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@ExperimentalTime
class AgendaViewModelTest : MviViewModelTest() {

    lateinit var sut: AgendaViewModel

    private val getTasksInteractor: GetTasksInteractor = mockk(relaxed = true)
    private val updateTaskInteractor: UpdateTaskInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = AgendaViewModel(
            getTasksInteractor,
            updateTaskInteractor,
        )
    }

    @Test
    fun `should expose correct state when land on screen`() = testBlocking {
        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.isPreviousDaySelected).isTrue()
            assertThat(lastState.isNextDaySelected).isTrue()
            assertThat(lastState.selectedDay).isEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `should expose tasks when load succeed`() = testBlocking {
        val expected = DEFAULT_TASKS
        coEvery { getTasksInteractor.observe() } returns flowOf(expected)

        sut.loadTasks()

        sut.state.test {
            assertThat(awaitItem().tasks).isEqualTo(Async.Success(expected))
        }
    }

    @Test
    fun `should expose previous date as disabled when trying to set selected day out of from range`() =
        testBlocking {

            sut.setSelectedDay(sut.state.value.calendarFromDate)

            sut.state.test {
                assertThat(expectMostRecentItem().isPreviousDaySelected).isFalse()
            }
        }

    @Test
    fun `should expose next date as disabled when trying to set next day out of until range`() =
        testBlocking {

            sut.setSelectedDay(sut.state.value.calendarUntilDate)

            sut.state.test {
                assertThat(expectMostRecentItem().isNextDaySelected).isFalse()
            }
        }

    @Test
    fun `should load task accordingly when mark task as done`() = testBlocking {
        val expected = DEFAULT_TASKS
        coEvery { getTasksInteractor.observe() } returns flowOf(expected)

        sut.loadTasks()
        sut.onMarkTask(expected.first().id, true)

        coVerify { updateTaskInteractor(UpdateTaskInteractor.Params(expected.first().id, true)) }
    }

    companion object {
        val DEFAULT_TASKS = listOf(
            Task(
                1L,
                "Task 1",
                LocalDate.now(),
                null,
                false
            ),
            Task(
                2L,
                "Task 2",
                LocalDate.now(),
                null,
                false
            )
        )
    }

}