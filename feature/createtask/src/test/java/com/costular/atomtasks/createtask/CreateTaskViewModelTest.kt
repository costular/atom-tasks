package com.costular.atomtasks.createtask

import app.cash.turbine.test
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.createtask.analytics.CreateTaskAnalytics
import com.costular.atomtasks.tasks.interactor.CreateTaskInteractor
import com.costular.atomtasks.core.InvokeError
import com.costular.atomtasks.core.InvokeStarted
import com.costular.atomtasks.core.InvokeSuccess
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

    private val createTaskInteractor: CreateTaskInteractor = mockk()
    private val atomAnalytics: AtomAnalytics = mockk(relaxed = true)

    lateinit var sut: CreateTaskViewModel

    @Before
    fun setUp() {
        sut = CreateTaskViewModel(
            createTaskInteractor = createTaskInteractor,
            atomAnalytics = atomAnalytics,
        )
    }

    @Test
    fun `should expose success when create task succeed`() = runTest {
        val name = "Task's name"
        val date = LocalDate.of(2022, 2, 10)
        val reminder = LocalTime.of(0, 0)
        coEvery {
            createTaskInteractor(
                CreateTaskInteractor.Params(
                    name,
                    date,
                    true,
                    reminder,
                ),
            )
        } returns flow {
            emit(InvokeStarted)
            emit(InvokeSuccess)
        }

        sut.createTask(name, date, reminder)

        sut.state.test {
            assertThat(expectMostRecentItem()).isInstanceOf(CreateTaskState.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose failure when create task fails`() = runTest {
        val exception = Exception("some error")

        coEvery {
            createTaskInteractor(any())
        } returns flow {
            emit(InvokeError(exception))
        }

        sut.createTask("whatever", LocalDate.now(), LocalTime.now())

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
            createTaskInteractor(
                CreateTaskInteractor.Params(
                    name,
                    date,
                    true,
                    reminder,
                ),
            )
        } returns flow {
            emit(InvokeStarted)
            emit(InvokeSuccess)
        }

        sut.createTask(name, date, reminder)

        verify(exactly = 1) {
            atomAnalytics.track(CreateTaskAnalytics.TaskCreated)
        }
    }
}
