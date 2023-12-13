package com.costular.atomtasks.createtask

import app.cash.turbine.test
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.InvokeError
import com.costular.atomtasks.core.InvokeStarted
import com.costular.atomtasks.core.InvokeSuccess
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.createtask.analytics.CreateTaskAnalytics
import com.costular.atomtasks.tasks.interactor.CreateTaskUseCase
import com.costular.atomtasks.tasks.model.CreateTaskError
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class CreateTaskViewModelTest : MviViewModelTest() {

    private val createTaskUseCase: CreateTaskUseCase = mockk()
    private val atomAnalytics: AtomAnalytics = mockk(relaxed = true)

    lateinit var sut: CreateTaskViewModel

    @Before
    fun setUp() {
        sut = CreateTaskViewModel(
            createTaskUseCase = createTaskUseCase,
            atomAnalytics = atomAnalytics,
        )
    }

    @Test
    fun `should expose success when create task succeed`() = runTest {
        val name = "Task's name"
        val date = LocalDate.of(2022, 2, 10)
        val reminder = LocalTime.of(0, 0)
        coEvery {
            createTaskUseCase(
                CreateTaskUseCase.Params(
                    name = name,
                    date = date,
                    reminderEnabled = true,
                    reminderTime = reminder,
                    recurrenceType = RecurrenceType.YEARLY,
                ),
            )
        } returns Either.Result(Unit)

        sut.createTask(
            name = name,
            date = date,
            reminder = reminder,
            recurrence = RecurrenceType.YEARLY,
        )

        sut.state.test {
            assertThat(expectMostRecentItem()).isInstanceOf(CreateTaskState.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose failure when create task fails`() = runTest {
        val today = LocalDate.now()
        val now = LocalTime.now()

        coEvery {
            createTaskUseCase(
                CreateTaskUseCase.Params(
                    "whatever",
                    date = today,
                    reminderEnabled = true,
                    reminderTime = now,
                    recurrenceType = RecurrenceType.YEARLY,
                )
            )
        } returns Either.Error(CreateTaskError.UnknownError)

        sut.createTask(
            name = "whatever",
            date = today,
            reminder = now,
            recurrence = RecurrenceType.YEARLY
        )

        sut.state.test {
            assertThat(expectMostRecentItem()).isInstanceOf(CreateTaskState.Failure::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should track event when create task successfully`() = runTest {
        val name = "Task's name"
        val date = LocalDate.of(2022, 2, 10)
        val reminder = LocalTime.of(0, 0)
        coEvery {
            createTaskUseCase(
                CreateTaskUseCase.Params(
                    name,
                    date,
                    true,
                    reminder,
                    RecurrenceType.WEEKDAYS,
                ),
            )
        } returns Either.Result(Unit)

        sut.createTask(
            name = name,
            date = date,
            reminder = reminder,
            recurrence = RecurrenceType.WEEKDAYS,
        )

        verify(exactly = 1) {
            atomAnalytics.track(CreateTaskAnalytics.TaskCreated)
        }
    }
}
