package com.costular.atomtasks.edittask

import app.cash.turbine.test
import com.costular.atomtasks.coretesting.MviViewModelTest
import com.costular.atomtasks.data.tasks.GetTaskByIdInteractor
import com.costular.atomtasks.data.tasks.UpdateTaskInteractor
import com.costular.atomtasks.data.util.taskToday
import com.costular.atomtasks.ui.features.edittask.EditTaskViewModel
import com.costular.atomtasks.ui.features.edittask.TaskState
import com.costular.core.Async
import com.costular.core.InvokeError
import com.costular.core.InvokeStarted
import com.costular.core.InvokeSuccess
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalTime::class)
class EditTaskViewModelTest : MviViewModelTest() {

    lateinit var sut: EditTaskViewModel

    private val getTaskByIdInteractor: GetTaskByIdInteractor = mockk(relaxed = true)
    private val updateTaskInteractor: UpdateTaskInteractor = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = EditTaskViewModel(
            getTaskByIdInteractor = getTaskByIdInteractor,
            updateTaskInteractor = updateTaskInteractor,
        )
    }

    @Test
    fun `should load task successfully`() = testBlocking {
        every { getTaskByIdInteractor.flow } returns flowOf(taskToday)

        sut.loadTask(taskToday.id)

        sut.state.test {
            assertThat(
                (expectMostRecentItem().taskState as TaskState.Success).taskId,
            ).isEqualTo(taskToday.id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit idle when state is created`() = testBlocking {
        sut.state.test {
            assertThat(awaitItem().taskState).isInstanceOf(TaskState.Idle::class.java)
        }
    }

    @Test
    fun `should emit loading when start load a task`() = testBlocking {
        coroutineTestDispatcher.pauseDispatcher()

        every { getTaskByIdInteractor.flow } returns flowOf(taskToday)

        sut.loadTask(taskToday.id)

        sut.state.test {
            assertThat(awaitItem().taskState).isInstanceOf(TaskState.Idle::class.java)
            assertThat(awaitItem().taskState).isInstanceOf(TaskState.Loading::class.java)
            coroutineTestDispatcher.resumeDispatcher()
            assertThat(awaitItem().taskState).isInstanceOf(TaskState.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should not be able to update if task has not been loaded`() = testBlocking {
        sut.editTask(
            name = "whatever",
            date = LocalDate.now(),
            reminder = null,
        )

        verify(exactly = 0) { updateTaskInteractor(any()) }
    }

    @Test
    fun `should emit loading when editing the task`() = testBlocking {
        every { getTaskByIdInteractor.flow } returns flowOf(taskToday)
        every { updateTaskInteractor(any()) } returns flowOf(InvokeStarted)

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(taskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
        )

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isInstanceOf(Async.Loading::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit success when edit task succeeded`() = testBlocking {
        every { getTaskByIdInteractor.flow } returns flowOf(taskToday)
        every { updateTaskInteractor(any()) } returns flowOf(InvokeStarted, InvokeSuccess)

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(taskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
        )

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isInstanceOf(Async.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit error when edit task fails`() = testBlocking {
        val exception = Exception("some error")
        every { getTaskByIdInteractor.flow } returns flowOf(taskToday)
        every { updateTaskInteractor(any()) } returns flowOf(InvokeError(exception))

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(taskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
        )

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isInstanceOf(Async.Failure::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
