package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.core.Either
import com.costular.atomtasks.tasks.fake.TaskRecurring
import com.costular.atomtasks.tasks.fake.TaskToday
import com.costular.atomtasks.tasks.model.PopulateRecurringTasksError
import com.costular.atomtasks.tasks.model.PopulateRecurringTasksError.NotRecurringTask
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PopulateRecurringTasksUseCaseTest {

    lateinit var sut: PopulateRecurringTasksUseCase

    private val tasksRepository: TasksRepository = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = PopulateRecurringTasksUseCase(tasksRepository)
    }

    @Test
    fun `Should return not recurring task error when call usecase given the task is not recurring`() =
        runTest {
            val task = TaskToday
            givenTask(task)

            val result = sut.invoke(PopulateRecurringTasksUseCase.Params(10L))

            assertThat(result).isEqualTo(Either.Error(NotRecurringTask))
        }

    @Test
    fun `Should return Either right when populate recurring given recurrence is daily`() = runTest {
        val task = TaskRecurring.copy(recurrenceType = RecurrenceType.DAILY)
        givenTask(task)

        val result = sut(PopulateRecurringTasksUseCase.Params(100L))

        assertThat(result).isEqualTo(Either.Result(Unit))
    }

    @Test
    fun `Should return Either left when populate recurring given create task throws an exception`() =
        runTest {
            val task = TaskRecurring.copy(recurrenceType = RecurrenceType.DAILY)
            givenTask(task)
            coEvery {
                tasksRepository.createTask(
                    any(),
                    any(),
                    any(),
                    any(),
                    any(),
                    any()
                )
            } throws Exception("")

            val result = sut(PopulateRecurringTasksUseCase.Params(100L))

            assertThat(result).isEqualTo(Either.Error(PopulateRecurringTasksError.UnknownError))
        }

    private fun givenTask(task: Task) {
        coEvery { tasksRepository.getTaskById(any()) } returns flowOf(task)
    }
}
