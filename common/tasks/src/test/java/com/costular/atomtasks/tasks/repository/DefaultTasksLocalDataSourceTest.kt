package com.costular.atomtasks.tasks.repository

import com.costular.atomtasks.data.database.TransactionRunner
import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.TaskAggregated
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.tasks.model.RecurrenceType
import com.costular.atomtasks.tasks.model.asString
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

    private val tasksDao: TasksDao = mockk(relaxed = true)
    private val reminderDao: ReminderDao = mockk(relaxUnitFun = true)
    private val transactionRunner: TransactionRunner = mockk(relaxed = true)

    @Before
    fun setUp() {
        sut = DefaultTasksLocalDataSource(
            tasksDao = tasksDao,
            reminderDao = reminderDao,
            transactionRunner = transactionRunner,
        )
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

        val updatedTask = taskAggregated.task.copy(
            name = "Whatever",
            recurrenceType = RecurrenceType.DAILY.asString(),
        )
        sut.updateTask(updatedTask)

        coVerify {
            tasksDao.update(updatedTask)
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
