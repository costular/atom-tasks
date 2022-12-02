package com.costular.atomtasks.agenda

import app.cash.turbine.test
import com.costular.atomtasks.agenda.DeleteTaskAction.Hidden
import com.costular.atomtasks.agenda.DeleteTaskAction.Shown
import com.costular.atomtasks.coretesting.MviViewModelTest
import com.costular.atomtasks.tasks.GetTasksInteractor
import com.costular.atomtasks.tasks.Task
import com.costular.atomtasks.tasks.interactor.RemoveTaskInteractor
import com.costular.atomtasks.tasks.interactor.UpdateTaskIsDoneInteractor
import com.costular.core.Async
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class AgendaViewModelTest : MviViewModelTest() {

    lateinit var sut: AgendaViewModel

    private val getTasksInteractor: GetTasksInteractor = mockk(relaxed = true)
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor = mockk(relaxed = true)
    private val removeTaskInteractor: RemoveTaskInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = AgendaViewModel(
            getTasksInteractor,
            updateTaskIsDoneInteractor,
            removeTaskInteractor,
        )
    }

    @Test
    fun `should expose correct state when land on screen`() = testBlocking {
        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.selectedDay).isEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `should expose tasks when load succeed`() = testBlocking {
        val expected = DEFAULT_TASKS
        coEvery { getTasksInteractor.flow } returns flowOf(expected)

        sut.loadTasks()

        sut.state.test {
            assertThat(awaitItem().tasks).isEqualTo(Async.Success(expected))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose today as selected day when tap on select today`() = runTest {
        sut.setSelectedDay(LocalDate.now().plusDays(10))
        sut.setSelectedDayToday()

        sut.state.test {
            assertThat(awaitItem().selectedDay).isEqualTo(LocalDate.now())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should load task accordingly when mark task as done`() = testBlocking {
        val expected = DEFAULT_TASKS
        coEvery { getTasksInteractor.flow } returns flowOf(expected)

        sut.loadTasks()
        sut.onMarkTask(expected.first().id, true)

        coVerify {
            updateTaskIsDoneInteractor(
                UpdateTaskIsDoneInteractor.Params(
                    expected.first().id, true,
                ),
            )
        }
    }

    @Test
    fun `should show delete task action when tap on delete`() =
        testBlocking {
            val tasks = DEFAULT_TASKS
            val taskId = DEFAULT_TASKS.first().id
            coEvery { getTasksInteractor.flow } returns flowOf(tasks)

            sut.loadTasks()
            sut.actionDelete(taskId)

            sut.state.test {
                assertThat(expectMostRecentItem().deleteTaskAction).isInstanceOf(Shown::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should set delete task action to hidden when dismiss dialog`() =
        testBlocking {
            val tasks = DEFAULT_TASKS
            val taskId = DEFAULT_TASKS.first().id
            coEvery { getTasksInteractor.flow } returns flowOf(tasks)

            sut.loadTasks()
            sut.actionDelete(taskId)
            sut.dismissDelete()

            sut.state.test {
                assertThat(expectMostRecentItem().deleteTaskAction).isInstanceOf(Hidden::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should load empty tasks when remove given there is only one existing task`() =
        testBlocking {
            val expected = emptyList<Task>()
            val taskId = DEFAULT_TASKS.first().id
            coEvery {
                getTasksInteractor.flow
            } returns flowOf(DEFAULT_TASKS) andThen flowOf(expected)

            sut.loadTasks()
            sut.actionDelete(taskId)
            sut.deleteTask(taskId)

            coVerify { removeTaskInteractor(RemoveTaskInteractor.Params(taskId)) }
        }

    @Test
    fun `should collapse header when select a day`() = runTest {
        sut.toggleHeader()
        sut.setSelectedDay(LocalDate.now().plusDays(1))

        sut.state.test {
            assertThat(expectMostRecentItem().isHeaderExpanded).isFalse()
            cancelAndIgnoreRemainingEvents()
        }
    }

    companion object {
        val DEFAULT_TASKS = listOf(
            Task(
                1L,
                "Task 1",
                LocalDate.now(),
                LocalDate.now(),
                null,
                false,
            ),
            Task(
                2L,
                "Task 2",
                LocalDate.now(),
                LocalDate.now(),
                null,
                false,
            ),
        )
    }
}
