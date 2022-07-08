package com.costular.atomtasks.agenda

import app.cash.turbine.test
import com.costular.atomtasks.domain.interactor.GetTasksInteractor
import com.costular.atomtasks.domain.interactor.RemoveTaskInteractor
import com.costular.atomtasks.domain.interactor.UpdateTaskIsDoneInteractor
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.agenda.DeleteTaskAction.Hidden
import com.costular.atomtasks.agenda.DeleteTaskAction.Shown
import com.costular.atomtasks.core_testing.MviViewModelTest
import com.costular.core.Async
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class AgendaViewModelTest : MviViewModelTest() {

    lateinit var sut: com.costular.atomtasks.agenda.AgendaViewModel

    private val getTasksInteractor: GetTasksInteractor = mockk(relaxed = true)
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor = mockk(relaxed = true)
    private val removeTaskInteractor: RemoveTaskInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = com.costular.atomtasks.agenda.AgendaViewModel(
            getTasksInteractor,
            updateTaskIsDoneInteractor,
            removeTaskInteractor,
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
            coEvery { getTasksInteractor.observe() } returns flowOf(tasks)

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
            coEvery { getTasksInteractor.observe() } returns flowOf(tasks)

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
                getTasksInteractor.observe()
            } returns flowOf(DEFAULT_TASKS) andThen flowOf(expected)

            sut.loadTasks()
            sut.actionDelete(taskId)
            sut.deleteTask(taskId)

            coVerify { removeTaskInteractor(RemoveTaskInteractor.Params(taskId)) }
        }

    companion object {
        val DEFAULT_TASKS = listOf(
            Task(
                1L,
                "Task 1",
                LocalDate.now(),
                null,
                false,
            ),
            Task(
                2L,
                "Task 2",
                LocalDate.now(),
                null,
                false,
            ),
        )
    }
}
