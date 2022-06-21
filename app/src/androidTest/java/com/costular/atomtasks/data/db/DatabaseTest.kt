package com.costular.atomtasks.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao
import com.costular.atomtasks.db.AtomRemindersDatabase
import com.google.common.truth.Truth.assertThat
import java.io.IOException
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private val testCoroutine: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var db: AtomRemindersDatabase
    private lateinit var tasksDao: TasksDao
    private lateinit var reminderDao: ReminderDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AtomRemindersDatabase::class.java,
        )
            .setTransactionExecutor(testCoroutine.asExecutor())
            .setQueryExecutor(testCoroutine.asExecutor())
            .allowMainThreadQueries()
            .build()
        tasksDao = db.getTasksDao()
        reminderDao = db.getRemindersDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun shouldAddTask() = testCoroutine.runBlockingTest {
        val task = fixtureTask(name = "whatever")

        val id = tasksDao.addTask(task)

        tasksDao.observeAllTasks().test {
            assertThat(awaitItem().size).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun shouldUpdateTaskEntity() = testCoroutine.runBlockingTest {
        val taskEntity = fixtureTask(name = "whatever")
        val taskId = tasksDao.addTask(taskEntity)

        val newName = "Edited task's name"
        val newDate = LocalDate.of(2022, 6, 13)

        tasksDao.updateTask(taskId, newDate, newName)
        val actual = tasksDao.getTaskById(taskId).take(1).toList().first()

        assertThat(actual.task.name).isEqualTo(newName)
        assertThat(actual.task.day).isEqualTo(newDate)
    }

    @Test
    fun shouldUpdateTaskReminder() = testCoroutine.runBlockingTest {
        val taskEntity = fixtureTask(name = "test")

        val taskId = tasksDao.addTask(taskEntity)

        val reminder = ReminderEntity(
            reminderId = 0L,
            time = LocalTime.now(),
            date = LocalDate.now(),
            isEnabled = true,
            taskId = taskId,
        )
        reminderDao.insertReminder(reminder)

        val newReminder = LocalTime.of(9, 0)
        reminderDao.updateReminder(taskId, newReminder)

        val actual = tasksDao.getTaskById(taskId).take(1).toList().first()

        assertThat(actual.reminder?.time).isEqualTo(newReminder)
    }

    private fun fixtureTask(
        name: String,
        isDone: Boolean = true,
        date: LocalDate = LocalDate.now(),
    ): TaskEntity = TaskEntity(
        id = 0L,
        createdAt = date,
        name = name,
        day = date,
        isDone = isDone,
    )
}
