package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.core.toResult
import com.costular.atomtasks.data.settings.IsAutoforwardTasksSettingEnabledUseCase
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.core.usecase.invoke
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
    private val editTaskUseCase: EditTaskUseCase = mockk(relaxed = true)

    private lateinit var sut: AutoforwardTasksUseCase

    @Before
    fun setUp() {
        sut = AutoforwardTasksUseCase(
            isAutoforwardTasksSettingEnabledUseCase,
            observeTasksUseCase,
            editTaskUseCase,
        )
    }

    @Test
    fun `should do nothing when move undone task is disabled`() = runTest {
        val date = LocalDate.now().minusDays(1)
        givenDisabledMoveUndoneTasks()

        sut(AutoforwardTasksUseCase.Params(date))

        verify(exactly = 0) { observeTasksUseCase.invoke(any()) }
        coVerify(exactly = 0) { editTaskUseCase.invoke(any()) }
    }

    @Test
    fun `should not update any task when all the day tasks are done given move undone tasks is enabled`() =
        runTest {
            givenEnabledMoveUndoneTasks()
            givenDoneTasks()

            sut(AutoforwardTasksUseCase.Params(Day))

            coVerify(exactly = 0) { editTaskUseCase.invoke(any()) }
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
                    editTaskUseCase.invoke(
                        EditTaskUseCase.Params(
                            task.id,
                            task.name,
                            task.day.plusDays(1),
                            task.reminder?.time,
                            task.recurrenceType,
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
        every { observeTasksUseCase.invoke(any()) } returns flowOf(UndoneTasks.toResult())
    }

    private fun givenDoneTasks() {
        every { observeTasksUseCase.invoke(any()) } returns flowOf(
            listOf(Task).toResult(),
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
            isRecurring = false,
            recurrenceEndDate = null,
            recurrenceType = null,
            parentId = null,
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
                isRecurring = false,
                recurrenceEndDate = null,
                recurrenceType = null,
                parentId = null,
            ),
            Task(
                id = 2L,
                name = "2",
                createdAt = LocalDate.now(),
                day = Day,
                reminder = null,
                isDone = false,
                position = 3,
                isRecurring = false,
                recurrenceEndDate = null,
                recurrenceType = null,
                parentId = null,
            ),
        )
    }
}
