package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.tasks.fake.TaskToday
import com.costular.atomtasks.tasks.fake.TaskRecurring
import com.costular.atomtasks.tasks.model.UpdateTaskIsDoneError
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class UpdateTaskIsDoneUseCaseTest {

    lateinit var sut: UpdateTaskIsDoneUseCase

    private val tasksRepository: TasksRepository = mockk()

    @Before
    fun setUp() {
        sut = UpdateTaskIsDoneUseCase(tasksRepository)
    }

    @Test
    fun `Should call mark task repository method when invoke usecase`() = runTest {
        val taskId = 100L
        val isDone = true

        sut(UpdateTaskIsDoneUseCase.Params(taskId, isDone))

        coVerify { tasksRepository.markTask(taskId, isDone) }
    }

    @Test
    fun `Should return Either result when invoke usecase given repository returned success`() =
        runTest {
            coEvery { tasksRepository.markTask(1L, false) } returns Unit
            coEvery { tasksRepository.getTaskById(1L) } returns flowOf(TaskToday)

            val result = sut(UpdateTaskIsDoneUseCase.Params(1L, false))

            assertThat(result).isEqualTo(Either.Result(Unit))
        }

    @Test
    fun `Should return Either error when invoke usecase given repostory threw an exception`() =
        runTest {
            coEvery { tasksRepository.markTask(1L, true) } throws Exception("")

            val result = sut(UpdateTaskIsDoneUseCase.Params(1L, true))

            assertThat(result).isEqualTo(Either.Error(UpdateTaskIsDoneError.UnknownError))
        }

    @Test
    fun `Should create next task when execute usecase given it's a recurrent task`() = runTest {
        coEvery { tasksRepository.markTask(1L, true) } returns Unit
        coEvery { tasksRepository.getTaskById(1L) } returns flowOf(TaskRecurring)

        sut(UpdateTaskIsDoneUseCase.Params(1L, true))

        coVerify {
            tasksRepository.createTask(
                TaskRecurring.name,
                TaskRecurring.day.plusWeeks(1),
                reminderEnabled = TaskRecurring.reminder != null,
                reminderTime = TaskRecurring.reminder?.time,
                recurrenceType = TaskRecurring.recurrenceType,
                parentId = TaskRecurring.parentId
            )
        }
    }

    @Test
    fun `Should create next task with parent id when execute usecase given it's a recurrent task without parent id`() =
        runTest {
            val task = TaskRecurring.copy(parentId = null)
            coEvery { tasksRepository.markTask(1L, true) } returns Unit
            coEvery { tasksRepository.getTaskById(1L) } returns flowOf(task)

            sut(UpdateTaskIsDoneUseCase.Params(1L, true))

            coVerify {
                tasksRepository.createTask(
                    task.name,
                    task.day.plusWeeks(1),
                    reminderEnabled = task.reminder != null,
                    reminderTime = task.reminder?.time,
                    recurrenceType = task.recurrenceType,
                    parentId = task.id,
                )
            }
        }

    @Test
    fun `Should not create another task when execute the usecase given that the task isn't recurrent`() =
        runTest {
            coEvery { tasksRepository.markTask(1L, true) } returns Unit
            coEvery { tasksRepository.getTaskById(1L) } returns flowOf(TaskToday)

            sut(UpdateTaskIsDoneUseCase.Params(1L, true))

            coVerify(exactly = 0) {
                tasksRepository.createTask(
                    name = any(),
                    date = any(),
                    reminderEnabled = any(),
                    reminderTime = any(),
                    recurrenceType = any(),
                    parentId = any(),
                )
            }
        }

    @Test
    fun `Should not create another task when execute the usecase given that the task is recurrent but it was mark as not done`() =
        runTest {
            coEvery { tasksRepository.markTask(1L, true) } returns Unit
            coEvery { tasksRepository.getTaskById(1L) } returns flowOf(TaskRecurring)

            sut(UpdateTaskIsDoneUseCase.Params(1L, false))

            coVerify(exactly = 0) {
                tasksRepository.createTask(
                    name = any(),
                    date = any(),
                    reminderEnabled = any(),
                    reminderTime = any(),
                    recurrenceType = any(),
                    parentId = any(),
                )
            }
        }
}
