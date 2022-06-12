package com.costular.atomtasks.data.tasks

import com.costular.atomtasks.domain.model.Reminder
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.domain.repository.TasksRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest {

    private lateinit var sut: TasksRepository

    private val localDataSource: TaskLocalDataSource = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = DefaultTasksRepository(localDataSource)
    }

    @Test
    fun `should call local data source add task when add task`() = runBlockingTest {
        val taskName = "task name"
        val taskDate = LocalDate.now()

        val taskSlot = slot<TaskEntity>()

        sut.createTask(
            name = taskName,
            date = taskDate,
            reminderEnabled = false,
            reminderTime = null,
        )

        coVerify { localDataSource.createTask(capture(taskSlot)) }
        assertThat(taskSlot.captured.name).isEqualTo(taskName)
    }

    @Test
    fun `should call local data source add reminder when add task with reminder`() =
        runBlockingTest {
            val taskName = "task name"
            val taskDate = LocalDate.of(2022, 6, 4)
            val taskReminderTime = LocalTime.of(9, 0)

            val reminderSlot = slot<ReminderEntity>()

            sut.createTask(
                name = taskName,
                date = taskDate,
                reminderEnabled = true,
                reminderTime = taskReminderTime,
            )

            coVerify { localDataSource.createReminderForTask(capture(reminderSlot)) }
            assertThat(reminderSlot.captured.date).isEqualTo(taskDate)
            assertThat(reminderSlot.captured.time).isEqualTo(taskReminderTime)
        }

    @Test
    fun `should get task by id`() = runBlockingTest {
        val taskId = 101L
        val taskName = "just whatever"
        val createdAt = LocalDate.now()
        val taskDay = LocalDate.now().plusDays(1)
        val taskReminder = LocalTime.now()
        val taskIsDone = true

        val taskResult = TaskEntity(
            id = taskId,
            createdAt = createdAt,
            name = taskName,
            day = taskDay,
            isDone = taskIsDone,
        )
        val result = TaskAggregated(
            task = taskResult,
            reminder = ReminderEntity(
                reminderId = 10L,
                time = taskReminder,
                date = taskDay,
                isEnabled = true,
                taskId = taskId,
            ),
        )
        val expected = Task(
            id = taskId,
            name = taskName,
            createdAt = createdAt,
            day = taskDay,
            reminder = Reminder(
                id = 100L,
                time = taskReminder,
                isEnabled = true,
                date = LocalDate.now(),
            ),
            isDone = true,
        )

        coEvery { localDataSource.getTaskById(taskId) } returns flowOf(result)

        val actual = sut.getTaskById(taskId).take(1).toList().first()

        assertThat(actual.name).isEqualTo(expected.name)
        assertThat(actual.createdAt).isEqualTo(expected.createdAt)
        assertThat(actual.day).isEqualTo(expected.day)
        assertThat(actual.isDone).isEqualTo(expected.isDone)
        assertThat(actual.reminder?.time).isEqualTo(expected.reminder?.time)
    }

    @Test
    fun `should call local data source update task when update task`() = runBlockingTest {
        val taskId = 10L
        val taskName = "Task name"
        val taskCreatedAt = LocalDate.now()
        val taskDay = LocalDate.of(2022, 6, 4)
        val taskIsDone = true

        sut.updateTask(taskId, taskDay, taskName)

        coVerify { localDataSource.updateTask(taskId, taskDay, taskName) }
    }
}
