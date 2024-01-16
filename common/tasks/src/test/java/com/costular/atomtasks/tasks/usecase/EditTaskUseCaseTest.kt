package com.costular.atomtasks.tasks.usecase

<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.repository.TasksRepository
========
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.costular.atomtasks.tasks.manager.TaskReminderManager
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
class EditTaskUseCaseTest {

    private lateinit var sut: EditTaskUseCase
========
class UpdateTaskUseCaseTest {

    private lateinit var sut: UpdateTaskUseCase
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt

    private val repository: TasksRepository = mockk(relaxed = true)
    private val taskReminderManager: TaskReminderManager = mockk(relaxed = true)

    @Before
    fun setUp() {
<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
        sut = EditTaskUseCase(repository, taskReminderManager)
========
        sut = UpdateTaskUseCase(repository, taskReminderManager)
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
    }

    @Test
    fun `should update task`() = runBlockingTest {
        val taskId = 10L
        val name = "New name"
        val newDay = LocalDate.now()

        sut(
<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
            EditTaskUseCase.Params(
========
            UpdateTaskUseCase.Params(
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
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
<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
            EditTaskUseCase.Params(
========
            UpdateTaskUseCase.Params(
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
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
<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
            EditTaskUseCase.Params(
========
            UpdateTaskUseCase.Params(
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
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
<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
            EditTaskUseCase.Params(
========
            UpdateTaskUseCase.Params(
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
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
<<<<<<<< HEAD:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/EditTaskUseCaseTest.kt
            EditTaskUseCase.Params(
========
            UpdateTaskUseCase.Params(
>>>>>>>> main:common/tasks/src/test/java/com/costular/atomtasks/tasks/usecase/UpdateTaskUseCaseTest.kt
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
