package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.settings.IsAutoforwardTasksSettingEnabledUseCase
import com.costular.atomtasks.tasks.ObserveTasksUseCase
import com.costular.atomtasks.tasks.Task
import com.costular.core.usecase.invoke
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AutoforwardTasksUseCaseTest {

    private val isAutoforwardTasksSettingEnabledUseCase: IsAutoforwardTasksSettingEnabledUseCase =
        mockk()
    private val observeTasksUseCase: ObserveTasksUseCase = mockk(relaxed = true)
    private val updateTaskUseCase: UpdateTaskUseCase = mockk(relaxed = true)

    private lateinit var sut: AutoforwardTasksUseCase

    @Before
    fun setUp() {
        sut = AutoforwardTasksUseCase(
            isAutoforwardTasksSettingEnabledUseCase,
            observeTasksUseCase,
            updateTaskUseCase,
        )
    }

    @Test
    fun `should do nothing when move undone task is disabled`() = runTest {
        val date = LocalDate.now().minusDays(1)
        givenDisabledMoveUndoneTasks()

        sut(AutoforwardTasksUseCase.Params(date))

        verify(exactly = 0) { observeTasksUseCase.invoke(any()) }
        coVerify(exactly = 0) { updateTaskUseCase.invoke(any()) }
    }

    @Test
    fun `should not update any task when all the day tasks are done given move undone tasks is enabled`() =
        runTest {
            givenEnabledMoveUndoneTasks()
            givenDoneTasks()

            sut(AutoforwardTasksUseCase.Params(Day))

            coVerify(exactly = 0) { updateTaskUseCase.invoke(any()) }
        }

    @Test
    fun `should update tasks when they have not been done given undone tasks is enabled`() =
        runTest {
            givenEnabledMoveUndoneTasks()
            givenUndoneTasks()

            sut(AutoforwardTasksUseCase.Params(Day))

            verify {
                observeTasksUseCase.invoke(ObserveTasksUseCase.Params(Day.minusDays(1)))
            }
            UndoneTasks.forEach { task ->
                coVerify {
                    updateTaskUseCase.invoke(
                        UpdateTaskUseCase.Params(
                            task.id,
                            task.name,
                            task.day.plusDays(1),
                            task.reminder?.isEnabled ?: false,
                            task.reminder?.time,
                        ),
                    )
                }
            }
        }

    private fun givenEnabledMoveUndoneTasks() {
        every { isAutoforwardTasksSettingEnabledUseCase.invoke() } returns flowOf(true)
    }

    private fun givenDisabledMoveUndoneTasks() {
        every { isAutoforwardTasksSettingEnabledUseCase.invoke() } returns flowOf(false)
    }

    private fun givenUndoneTasks() {
        every { observeTasksUseCase.invoke(any()) } returns flowOf(UndoneTasks)
    }

    private fun givenDoneTasks() {
        every { observeTasksUseCase.invoke(any()) } returns flowOf(
            listOf(Task),
        )
    }

    private companion object {
        val Day = LocalDate.now()
        val Task = Task(
            id = 1L,
            name = "1",
            createdAt = LocalDate.now(),
            day = Day,
            reminder = null,
            isDone = true,
            position = 1,
        )
        val UndoneTasks = listOf(
            Task(
                id = 1L,
                name = "1",
                createdAt = LocalDate.now(),
                day = Day,
                reminder = null,
                isDone = false,
                position = 2,
            ),
            Task(
                id = 2L,
                name = "2",
                createdAt = LocalDate.now(),
                day = Day,
                reminder = null,
                isDone = false,
                position = 3,
            ),
        )
    }
}
