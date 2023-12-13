package com.costular.atomtasks.tasks.interactor

import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.tasks.model.Reminder
import com.costular.atomtasks.tasks.model.Task
import com.costular.atomtasks.tasks.repository.DefaultTasksRepository
import com.costular.atomtasks.tasks.repository.TaskLocalDataSource
import com.costular.atomtasks.tasks.repository.TasksRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.slot
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
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
    fun `should call local data source add task when add task`() = runTest {
        val taskName = "task name"
        val taskDate = LocalDate.now()

        val taskSlot = slot<TaskEntity>()

        sut.createTask(
            name = taskName,
            date = taskDate,
            reminderEnabled = false,
            reminderTime = null,
            recurrenceType = null,
        )

        coVerify { localDataSource.createTask(capture(taskSlot)) }
        assertThat(taskSlot.captured.name).isEqualTo(taskName)
    }

    @Test
    fun `should call local data source add reminder when add task with reminder`() = runTest {
        val taskName = "task name"
        val taskDate = LocalDate.of(2022, 6, 4)
        val taskReminderTime = LocalTime.of(9, 0)
        val reminderEnabled = true

        val taskId = sut.createTask(
            name = taskName,
            date = taskDate,
            reminderEnabled = reminderEnabled,
            reminderTime = taskReminderTime,
            recurrenceType = null,
        )

        coVerify {
            localDataSource.createReminderForTask(
                taskReminderTime,
                taskDate,
                reminderEnabled,
                taskId,
            )
        }
    }

    @Test
    fun `should get task by id`() = runTest {
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
                date = LocalDate.now(),
            ),
            isDone = true,
            position = 1,
            isRecurring = false,
            recurrenceEndDate = null,
            recurrenceType = null,
            parentId = null,
        )

        coEvery { localDataSource.getTaskById(taskId) } returns flowOf(result)

        val actual = sut.getTaskById(taskId).first()

        assertThat(actual.name).isEqualTo(expected.name)
        assertThat(actual.createdAt).isEqualTo(expected.createdAt)
        assertThat(actual.day).isEqualTo(expected.day)
        assertThat(actual.isDone).isEqualTo(expected.isDone)
        assertThat(actual.reminder?.time).isEqualTo(expected.reminder?.time)
    }

    @Test
    fun `should call local data source update task when update task`() = runTest {
        val taskId = 10L
        val taskName = "Task name"
        val taskDay = LocalDate.of(2022, 6, 4)

        sut.updateTask(taskId, taskDay, taskName)

        coVerify { localDataSource.updateTask(taskId, taskDay, taskName) }
    }

    @Test
    fun `should call local data source move task when move task`() = runTest {
        val taskId = 1L
        val from = 1
        val to = 3

        sut.moveTask(LocalDate.now(), from, to)

        coVerify(exactly = 1) { localDataSource.moveTask(LocalDate.now(), from, to) }
    }
}
