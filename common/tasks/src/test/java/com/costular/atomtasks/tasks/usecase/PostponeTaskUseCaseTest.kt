package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.model.Reminder
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.core.toError
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PostponeTaskUseCaseTest {

    lateinit var sut: PostponeTaskUseCase

    private val getTaskByIdUseCase: GetTaskByIdUseCase = mockk(relaxed = true)
    private val updateTaskReminderInteractor: UpdateTaskReminderInteractor = mockk(relaxed = true)
    private val taskReminderManager: TaskReminderManager = mockk(relaxed = true)
    private val editTaskUseCase: EditTaskUseCase = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = PostponeTaskUseCase(
            getTaskByIdUseCase = getTaskByIdUseCase,
            updateTaskReminderInteractor = updateTaskReminderInteractor,
            taskReminderManager = taskReminderManager,
            editTaskUseCase = editTaskUseCase,
        )
    }

    @Test
    fun `Should return error when postpone a task given the task has no reminder`() = runTest {
        givenTaskWithoutReminder()

        val result = sut.invoke(
            PostponeTaskUseCase.Params(
                taskId = 1L,
                day = LocalDate.now(),
                time = LocalTime.now().plusHours(1),
            )
        )

        Truth.assertThat(result).isEqualTo(PostponeTaskFailure.MissingReminder.toError())
    }

    @Test
    fun `Should update task reminder when postpone task given the task has reminder`() = runTest {
        givenTaskWithReminder()

        val taskId = 10L
        val day = LocalDate.now().plusDays(1)
        val time = LocalTime.of(21, 0)

        sut.invoke(
            PostponeTaskUseCase.Params(
                taskId = taskId,
                day = day,
                time = time,
            )
        )

        coEvery {
            updateTaskReminderInteractor.invoke(
                UpdateTaskReminderInteractor.Params(
                    taskId = taskId,
                    time = time,
                    date = day,
                )
            )
        }
    }

    @Test
    fun `Should set the new reminder when postpone a task given it has a remidner`() = runTest {
        givenTaskWithReminder()

        val taskId = 10L
        val day = LocalDate.now().plusDays(1)
        val time = LocalTime.of(21, 0)

        sut.invoke(
            PostponeTaskUseCase.Params(
                taskId = taskId,
                day = day,
                time = time,
            )
        )

        coEvery {
            taskReminderManager.set(
                taskId = taskId,
                localDateTime = day.atTime(time),
            )
        }
    }

    @Test
    fun `Should update task due date when postpone a task`() = runTest {
        givenTaskWithReminder()

        val taskId = 1L
        val day = LocalDate.now().plusDays(1)
        val time = LocalTime.of(21, 0)

        sut.invoke(
            PostponeTaskUseCase.Params(
                taskId = taskId,
                day = day,
                time = time,
            )
        )

        coEvery {
            editTaskUseCase.invoke(
                EditTaskUseCase.Params(
                    taskId = taskId,
                    name = "Whatever",
                    date = day,
                    reminderTime = time,
                    recurrenceType = null,
                    recurringUpdateStrategy = null,
                )
            )
        }
    }

    private fun givenTaskWithoutReminder() {
        coEvery { getTaskByIdUseCase(any()) } returns flowOf(
            Task(
                id = 1L,
                name = "Whatever",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = null,
                isDone = false,
                position = 0,
                isRecurring = false,
                recurrenceEndDate = null,
                recurrenceType = null,
                parentId = null,
            )
        )
    }

    private fun givenTaskWithReminder() {
        coEvery { getTaskByIdUseCase(any()) } returns flowOf(
            Task(
                id = 1L,
                name = "Whatever",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                reminder = Reminder(
                    id = 1L,
                    time = LocalTime.of(9, 0),
                    date = LocalDate.now().plusDays(1),
                ),
                isDone = false,
                position = 0,
                isRecurring = false,
                recurrenceEndDate = null,
                recurrenceType = null,
                parentId = null,
            )
        )
    }
}
