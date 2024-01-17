package com.costular.atomtasks.tasks.repository

import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.time.LocalDate
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultTasksLocalDataSourceTest {

    lateinit var sut: TaskLocalDataSource

    private val tasksDao: TasksDao = mockk(relaxUnitFun = true)
    private val reminderDao: ReminderDao = mockk(relaxUnitFun = true)

    @Before
    fun setUp() {
        sut = DefaultTasksLocalDataSource(
            tasksDao = tasksDao,
            reminderDao = reminderDao,
        )
    }

    @Test
    fun `Should generate new position when update task given the day is different`() = runTest {
        val id = 1121L
        val lastPosition = 1
        val newDay = LocalDate.now().plusDays(1)

        val taskAggregated = TaskAggregated(
            task = TaskEntity(
                id = id,
                name = "Pierre Jimenez",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                isDone = false,
                position = 1,
                isRecurring = false,
                recurrenceType = null,
                recurrenceEndDate = null,
                parentId = null,
            ),
            reminder = null,
        )
        coEvery { tasksDao.getTaskById(id) } returns flowOf(taskAggregated)
        coEvery { tasksDao.getMaxPositionForDate(newDay) } returns lastPosition

        sut.updateTask(
            id,
            day = newDay,
            name = "Whatever",
            recurrenceType = null,
        )

        coVerify {
            tasksDao.updateTask(
                taskId = id,
                day = newDay,
                name = "Whatever",
                position = lastPosition + 1,
                isRecurring = false,
                recurrence = null,
            )
        }
    }

    @Test
    fun `Should call task dao accordingly when update task`() = runTest {
        val id = 1121L

        val taskAggregated = TaskAggregated(
            task = TaskEntity(
                id = id,
                name = "Pierre Jimenez",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                isDone = false,
                position = 1,
                isRecurring = false,
                recurrenceType = null,
                recurrenceEndDate = null,
                parentId = null,
            ),
            reminder = null,
        )
        coEvery { tasksDao.getTaskById(id) } returns flowOf(taskAggregated)

        sut.updateTask(
            id,
            day = taskAggregated.task.day,
            name = "Whatever",
            recurrenceType = RecurrenceType.DAILY,
        )

        coVerify {
            tasksDao.updateTask(
                taskId = id,
                day = taskAggregated.task.day,
                name = "Whatever",
                position = taskAggregated.task.position,
                isRecurring = true,
                recurrence = "daily",
            )
        }
    }

    @Test
    fun `Should remove future occurrences for recurring task when update task recurring task`() =
        runTest {
            val id = 1121L

            val taskAggregated = TaskAggregated(
                task = TaskEntity(
                    id = id,
                    name = "Pierre Jimenez",
                    createdAt = LocalDate.now(),
                    day = LocalDate.now(),
                    isDone = false,
                    position = 1,
                    isRecurring = true,
                    recurrenceType = "daily",
                    recurrenceEndDate = null,
                    parentId = null,
                ),
                reminder = null,
            )
            coEvery { tasksDao.getTaskById(id) } returns flowOf(taskAggregated)
            coEvery { tasksDao.getMaxPositionForDate(any()) } returns 1
            coEvery { tasksDao.createTask(any()) } returns 1L

            sut.updateTask(
                id,
                day = taskAggregated.task.day,
                name = "Whatever",
                recurrenceType = RecurrenceType.WEEKLY,
            )

            coVerify(exactly = 1) {
                tasksDao.removeChildrenTasks(id)
            }

            coVerify(exactly = 1) {
                tasksDao.updateTask(
                    taskId = id,
                    day = taskAggregated.task.day,
                    name = "Whatever",
                    position = 1,
                    isRecurring = true,
                    recurrence = "weekly",
                )
            }
        }

    @Test
    fun `Should call dao when count future occurrences for recurring task`() = runTest {
        val id = 10L
        val expectedCount = 5

        val taskAggregated = TaskAggregated(
            task = TaskEntity(
                id = id,
                name = "Pierre Jimenez",
                createdAt = LocalDate.now(),
                day = LocalDate.now(),
                isDone = false,
                position = 1,
                isRecurring = false,
                recurrenceType = null,
                recurrenceEndDate = null,
                parentId = null,
            ),
            reminder = null,
        )
        coEvery { tasksDao.getTaskById(id) } returns flowOf(taskAggregated)
        coEvery {
            tasksDao.countFutureOccurrences(10L, LocalDate.now())
        } returns expectedCount

        val result = sut.numberFutureOccurrences(10L, LocalDate.now())

        assertThat(result).isEqualTo(expectedCount)
    }
}
