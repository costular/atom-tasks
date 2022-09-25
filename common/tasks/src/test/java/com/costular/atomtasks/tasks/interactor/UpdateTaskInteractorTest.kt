package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.tasks.TasksRepository
import com.costular.atomtasks.tasks.manager.ReminderManager
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class UpdateTaskInteractorTest {

    private lateinit var sut: UpdateTaskInteractor

    private val repository: TasksRepository = mockk(relaxed = true)
    private val reminderManager: ReminderManager = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = UpdateTaskInteractor(repository, reminderManager)
    }

    @Test
    fun `should update task`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()

        sut.executeSync(
            UpdateTaskInteractor.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderEnabled = false,
                reminderTime = null,
            ),
        )

        coVerify { repository.updateTask(taskId, newDay, name) }
    }

    @Test
    fun `should update reminder when is enabled not null`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()
        val reminder = LocalTime.of(9, 0)

        sut.executeSync(
            UpdateTaskInteractor.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderEnabled = true,
                reminderTime = reminder,
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

        sut.executeSync(
            UpdateTaskInteractor.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderEnabled = true,
                reminderTime = reminder,
            ),
        )

        coVerify { reminderManager.set(taskId, reminder.atDate(newDay)) }
    }

    @Test
    fun `should remove reminder when reminder is null and not enabled`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()
        val reminder = null

        sut.executeSync(
            UpdateTaskInteractor.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderEnabled = false,
                reminderTime = reminder,
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

        sut.executeSync(
            UpdateTaskInteractor.Params(
                taskId = taskId,
                name = name,
                date = newDay,
                reminderEnabled = false,
                reminderTime = reminder,
            ),
        )

        coVerify { reminderManager.cancel(taskId) }
    }
}
