package com.costular.atomtasks.tasks.usecase

import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.repository.TasksRepository
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class EditTaskUseCaseTest {

    private lateinit var sut: EditTaskUseCase

    private val repository: TasksRepository = mockk(relaxed = true)
    private val taskReminderManager: TaskReminderManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = EditTaskUseCase(repository, taskReminderManager)
    }

    @Test
    fun `should update task`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()

        sut(
            EditTaskUseCase.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderTime = null,
                recurrenceType = null,
            ),
        )

        coVerify { repository.updateTask(taskId, newDay, name, null) }
    }

    @Test
    fun `should update reminder when is enabled not null`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()
        val reminder = LocalTime.of(9, 0)

        sut(
            EditTaskUseCase.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderTime = reminder,
                recurrenceType = null,
            ),
        )

        coVerify { repository.updateTaskReminder(taskId, reminder, newDay) }
    }

    @Test
    fun `should set reminder when reminder is enabled and not null`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()
        val reminder = LocalTime.of(9, 0)

        sut(
            EditTaskUseCase.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderTime = reminder,
                recurrenceType = null,
            ),
        )

        coVerify { taskReminderManager.set(taskId, reminder.atDate(newDay)) }
    }

    @Test
    fun `should remove reminder when reminder is null and not enabled`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()
        val reminder = null

        sut(
            EditTaskUseCase.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderTime = reminder,
                recurrenceType = null,
            ),
        )

        coVerify { repository.removeReminder(taskId) }
    }

    @Test
    fun `should set reminder when reminder is not null`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()
        val reminder = null

        sut(
            EditTaskUseCase.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderTime = reminder,
                recurrenceType = null,
            ),
        )

        coVerify { taskReminderManager.cancel(taskId) }
    }
}
