package com.costular.atomtasks.agenda

import app.cash.turbine.test
import com.costular.atomtasks.agenda.DeleteTaskAction.Hidden
import com.costular.atomtasks.agenda.DeleteTaskAction.Shown
import com.costular.atomtasks.agenda.analytics.AgendaAnalytics
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.coreui.date.asDay
import com.costular.atomtasks.tasks.ObserveTasksUseCase
import com.costular.atomtasks.tasks.Task
import com.costular.atomtasks.tasks.interactor.MoveTaskUseCase
import com.costular.atomtasks.tasks.interactor.RemoveTaskInteractor
import com.costular.atomtasks.tasks.interactor.UpdateTaskIsDoneInteractor
import com.costular.atomtasks.tasks.manager.AutoforwardManager
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.burnoutcrew.reorderable.ItemPosition
import org.junit.Before
import org.junit.Test

@ExperimentalTime
class AgendaViewModelTest : MviViewModelTest() {

    lateinit var sut: AgendaViewModel

    private val observeTasksUseCase: ObserveTasksUseCase = mockk(relaxed = true)
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor = mockk(relaxed = true)
    private val removeTaskInteractor: RemoveTaskInteractor = mockk(relaxed = true)
    private val autoforwardManager: AutoforwardManager = mockk(relaxed = true)
    private val moveTaskUseCase: MoveTaskUseCase = mockk(relaxed = true)
    private val atomAnalytics: AtomAnalytics = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = AgendaViewModel(
            observeTasksUseCase = observeTasksUseCase,
            updateTaskIsDoneInteractor = updateTaskIsDoneInteractor,
            removeTaskInteractor = removeTaskInteractor,
            autoforwardManager = autoforwardManager,
            moveTaskUseCase = moveTaskUseCase,
            atomAnalytics = atomAnalytics,
        )
    }

    @Test
    fun `should expose correct state when land on screen`() = runTest {
        sut.state.test {
            val lastState = expectMostRecentItem()
            assertThat(lastState.selectedDay.date).isEqualTo(LocalDate.now())
        }
    }

    @Test
    fun `should expose tasks when load succeed`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)

        sut.loadTasks()

        sut.state.test {
            assertThat(awaitItem().tasks).isEqualTo(TasksState.Success(expected.toImmutableList()))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should expose today as selected day when tap on select today`() = runTest {
        sut.setSelectedDay(LocalDate.now().plusDays(10))
        sut.setSelectedDayToday()

        sut.state.test {
            assertThat(awaitItem().selectedDay.date).isEqualTo(LocalDate.now())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should load task accordingly when mark task as done`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)

        sut.loadTasks()
        sut.onMarkTask(expected.first().id, true)

        coVerify {
            updateTaskIsDoneInteractor(
                UpdateTaskIsDoneInteractor.Params(
                    taskId = expected.first().id,
                    isDone = true,
                ),
            )
        }
    }

    @Test
    fun `should show delete task action when tap on delete`() =
        runTest {
            val tasks = DEFAULT_TASKS
            val taskId = DEFAULT_TASKS.first().id
            coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(tasks)

            sut.loadTasks()
            sut.actionDelete(taskId)

            sut.state.test {
                assertThat(expectMostRecentItem().deleteTaskAction).isInstanceOf(Shown::class.java)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should set delete task action to hidden when dismiss dialog`() =
        runTest {
            val tasks = DEFAULT_TASKS
            val taskId = DEFAULT_TASKS.first().id
            coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(tasks)

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
        runTest {
            val expected = emptyList<Task>()
            val taskId = DEFAULT_TASKS.first().id
            coEvery {
                observeTasksUseCase.invoke(any())
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

    @Test
    fun `should update state when drag task`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)
        val from = 1
        val to = 2

        sut.loadTasks()
        sut.onDragTask(ItemPosition(from, TASK1_ID), ItemPosition(to, TASK2ID))

        val state = sut.state.value
        val tasks = (state.tasks as TasksState.Success).data
        assertThat(tasks.first().id).isEqualTo(TASK2ID)
        assertThat(tasks.last().id).isEqualTo(TASK1_ID)
        coVerify(exactly = 0) { moveTaskUseCase(any()) }
    }

    @Test
    fun `should call usecase when moving a task given tasks were loaded successfully`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)
        val from = 0
        val to = 1

        sut.loadTasks()
        sut.onMoveTask(from, to)

        coVerify(exactly = 1) {
            moveTaskUseCase(MoveTaskUseCase.Params(LocalDate.now(), from + 1, to + 1))
        }
    }

    @Test
    fun `should call autoforward usecase when land on the screen`() = runTest {
        coVerify(exactly = 1) {
            autoforwardManager.scheduleOrCancelAutoforwardTasks()
        }
    }

    @Test
    fun `should track event when expand header`() = runTest {
        sut.toggleHeader()

        verify {
            atomAnalytics.track(AgendaAnalytics.ExpandCalendar)
        }
    }

    @Test
    fun `should track event when collapse header`() = runTest {
        sut.toggleHeader()
        sut.toggleHeader()

        verify(exactly = 1) {
            atomAnalytics.track(AgendaAnalytics.CollapseCalendar)
        }
    }

    @Test
    fun `should track event when moving a task`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)
        val from = 0
        val to = 1

        sut.loadTasks()
        sut.onMoveTask(from, to)

        verify { atomAnalytics.track(AgendaAnalytics.OrderTask) }
    }

    @Test
    fun `should track event when mark task as done`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)

        sut.loadTasks()
        sut.onMarkTask(expected.first().id, true)

        verify {
            atomAnalytics.track(AgendaAnalytics.MarkTaskAsDone)
        }
    }

    @Test
    fun `should track event when mark task as not done`() = runTest {
        val expected = DEFAULT_TASKS
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(expected)

        sut.loadTasks()
        sut.onMarkTask(expected.last().id, false)

        verify {
            atomAnalytics.track(AgendaAnalytics.MarkTaskAsNotDone)
        }
    }

    @Test
    fun `should track show confirm delete when tap on delete`() = runTest {
        val tasks = DEFAULT_TASKS
        val taskId = DEFAULT_TASKS.first().id
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(tasks)

        sut.loadTasks()
        sut.actionDelete(taskId)

        verify {
            atomAnalytics.track(AgendaAnalytics.ShowConfirmDeleteDialog)
        }
    }

    @Test
    fun `should track confirm delete when confirm dialog`() = runTest {
        val tasks = DEFAULT_TASKS
        val taskId = DEFAULT_TASKS.first().id
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(tasks)

        sut.loadTasks()
        sut.actionDelete(taskId)
        sut.deleteTask(taskId)

        verify(exactly = 1) {
            atomAnalytics.track(AgendaAnalytics.ConfirmDelete)
        }
    }

    @Test
    fun `should track navigate to day when select a new day`() = runTest {
        val day = LocalDate.of(2023, 9, 16)

        sut.setSelectedDay(day)

        verify(exactly = 1) {
            atomAnalytics.track(AgendaAnalytics.NavigateToDay(day.toString()))
        }
    }

    @Test
    fun `should track cancel delete when dismiss confirm delete dialog`() = runTest {
        val tasks = DEFAULT_TASKS
        val taskId = DEFAULT_TASKS.first().id
        coEvery { observeTasksUseCase.invoke(any()) } returns flowOf(tasks)

        sut.loadTasks()
        sut.actionDelete(taskId)
        sut.dismissDelete()

        verify(exactly = 1) {
            atomAnalytics.track(AgendaAnalytics.CancelDelete)
        }
    }

    companion object {
        const val TASK1_ID = 1L
        const val TASK2ID = 2L
        val DEFAULT_TASKS = listOf(
            Task(
                id = TASK1_ID,
                name = "Task 1",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = null,
                isDone = false,
                position = 1,
            ),
            Task(
                id = TASK2ID,
                name = "Task 2",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = null,
                isDone = true,
                position = 2,
            ),
        )
    }
}
