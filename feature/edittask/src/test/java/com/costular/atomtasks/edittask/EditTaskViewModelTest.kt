package com.costular.atomtasks.edittask

import app.cash.turbine.test
import com.costular.atomtasks.core.Either
import com.costular.atomtasks.core.testing.MviViewModelTest
import com.costular.atomtasks.core.toError
import com.costular.atomtasks.feature.edittask.EditRecurringTaskResponse.THIS
import com.costular.atomtasks.feature.edittask.EditTaskViewModel
import com.costular.atomtasks.feature.edittask.SavingState
import com.costular.atomtasks.feature.edittask.TaskState
import com.costular.atomtasks.tasks.fake.TaskRecurring
import com.costular.atomtasks.tasks.fake.TaskToday
import com.costular.atomtasks.tasks.model.RecurringUpdateStrategy
import com.costular.atomtasks.tasks.model.UpdateTaskUseCaseError
import com.costular.atomtasks.tasks.usecase.EditTaskUseCase
import com.costular.atomtasks.tasks.usecase.GetTaskByIdUseCase
import com.google.common.truth.Truth.assertThat
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class EditTaskViewModelTest : MviViewModelTest() {

    lateinit var sut: EditTaskViewModel

    private val getTaskByIdUseCase: GetTaskByIdUseCase = mockk(relaxed = true)
    private val editTaskUseCase: EditTaskUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = EditTaskViewModel(
            getTaskByIdUseCase = getTaskByIdUseCase,
            editTaskUseCase = editTaskUseCase,
        )
    }

    @Test
    fun `should load task successfully`() = runTest {
        givenTask()

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
            recurrenceType = null,
            recurringUpdateStrategy = null,
        )

        coVerify(exactly = 0) { editTaskUseCase(any()) }
    }

    @Test
    fun `should emit success when edit task succeeded`() = runTest {
        givenTask()
        coEvery { editTaskUseCase.invoke(any()) } returns Either.Result(Unit)

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
            recurrenceType = null,
            recurringUpdateStrategy = null,
        )

        sut.state.test {
            assertThat(expectMostRecentItem().savingTask).isInstanceOf(SavingState.Success::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should emit error when edit task fails`() = runTest {
        givenTask()
        coEvery {
            editTaskUseCase.invoke(any())
        } returns UpdateTaskUseCaseError.UnknownError.toError()

        val newTask = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskToday.id)
        sut.editTask(
            name = newTask,
            date = newDate,
            reminder = newReminder,
            recurrenceType = null,
            recurringUpdateStrategy = null,
        )

        assertThat(sut.state.value.savingTask).isInstanceOf(SavingState.Failure::class.java)
    }

    @Test
    fun `should set taskToSave successfully when update recurring task`() = runTest {
        givenRecurringTask()

        val newName = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskRecurring.id)

        sut.editTask(
            name = newName,
            date = newDate,
            reminder = newReminder,
            recurrenceType = null,
            recurringUpdateStrategy = null,
        )

        assertThat(sut.state.value.taskToSave?.date).isEqualTo(newDate)
        assertThat(sut.state.value.taskToSave?.name).isEqualTo(newName)
        assertThat(sut.state.value.taskToSave?.reminder).isEqualTo(newReminder)
    }

    @Test
    fun `should set taskToSave to null when cancel edition given task is recurring`() = runTest {
        givenRecurringTask()

        val newName = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskToday.id)

        sut.editTask(
            name = newName,
            date = newDate,
            reminder = newReminder,
            recurrenceType = null,
            recurringUpdateStrategy = null,
        )
        sut.cancelRecurringEdition()

        assertThat(sut.state.value.taskToSave).isNull()
    }

    @Test
    fun `should edit task properly when confirm update recurring`() = runTest {
        givenRecurringTask()
        givenSuccessfulEditTaskUseCase()

        val newName = "whatever"
        val newDate = LocalDate.now().plusDays(1)
        val newReminder = LocalTime.of(10, 0)

        sut.loadTask(TaskRecurring.id)

        sut.editTask(
            name = newName,
            date = newDate,
            reminder = newReminder,
            recurrenceType = null,
            recurringUpdateStrategy = null,
        )

        sut.confirmRecurringEdition(THIS)

        coVerify {
            editTaskUseCase(
                EditTaskUseCase.Params(
                    TaskRecurring.id,
                    newName,
                    newDate,
                    newReminder,
                    null,
                    RecurringUpdateStrategy.SINGLE,
                )
            )
        }
    }

    private fun givenSuccessfulEditTaskUseCase() {
        coEvery { editTaskUseCase.invoke(any()) } returns Either.Result(Unit)
    }

    private fun givenTask() {
        coEvery { getTaskByIdUseCase.invoke(any()) } returns flowOf(TaskToday)
    }

    private fun givenRecurringTask() {
        coEvery { getTaskByIdUseCase.invoke(any()) } returns flowOf(TaskRecurring)
    }
}
