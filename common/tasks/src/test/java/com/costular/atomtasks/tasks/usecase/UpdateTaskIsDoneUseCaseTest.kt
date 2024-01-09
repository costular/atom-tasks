package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.tasks.fake.TaskToday
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
}
