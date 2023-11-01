package com.costular.atomtasks.edittask

import app.cash.turbine.test
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.tasks.fake.TaskToday
import com.costular.atomtasks.tasks.interactor.GetTaskByIdUseCase
import com.costular.atomtasks.tasks.interactor.UpdateTaskUseCase
import com.costular.atomtasks.ui.features.edittask.EditTaskViewModel
import com.costular.atomtasks.ui.features.edittask.SavingState
import com.costular.atomtasks.ui.features.edittask.TaskState
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class EditTaskViewModelTest : MviViewModelTest() {

    lateinit var sut: EditTaskViewModel

    private val getTaskByIdUseCase: GetTaskByIdUseCase = mockk(relaxed = true)
    private val updateTaskUseCase: UpdateTaskUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = EditTaskViewModel(
            getTaskByIdUseCase = getTaskByIdUseCase,
            updateTaskUseCase = updateTaskUseCase,
        )
    }

    @Test
    fun `should load task successfully`() = runTest {
        coEvery { getTaskByIdUseCase.invoke(any()) } returns flowOf(TaskToday)

        sut.loadTask(TaskToday.id)

        sut.state.test {
            assertThat(
                (expectMostRecentItem().taskState as TaskState.Success).taskId,
            ).isEqualTo(TaskToday.id)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit idle when state is created`() = runTest {
        sut.state.test {
            assertThat(awaitItem().taskState).isInstanceOf(TaskState.Idle::class.java)
        }
    }

    @Test
    fun `should not be able to update if task has not been loaded`() = runTest {
        sut.editTask(
            name = "whatever",
            date = LocalDate.now(),
            reminder = null,
        )

        coVerify(exactly = 0) { updateTaskUseCase(any()) }
    }

    @Test
    fun `should emit success when edit task succeeded`() = runTest {
        coEvery { getTaskByIdUseCase.invoke(any()) } returns flowOf(TaskToday)

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
        )

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isInstanceOf(SavingState.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit error when edit task fails`() = runTest {
        val exception = Exception("some error")
        coEvery { getTaskByIdUseCase.invoke(any()) } returns flowOf(TaskToday)
        coEvery { updateTaskUseCase.invoke(any()) } throws exception

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
        )

        assertThat(sut.state.value.savingTask).isInstanceOf(SavingState.Failure::class.java)
    }
}
