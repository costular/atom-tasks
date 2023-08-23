package com.costular.atomtasks.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao
import com.google.common.truth.Truth
import java.io.IOException
import java.time.LocalDate
import kotlin.time.ExperimentalTime
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    private lateinit var db: AtomTasksDatabase
    private lateinit var tasksDao: TasksDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            AtomTasksDatabase::class.java,
        ).build()
        tasksDao = db.getTasksDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAddTask() = runTest {
        val task = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now(),
            isDone = true,
        )

        tasksDao.addTask(task)

        tasksDao.observeAllTasks().test {
            val item = awaitItem()
            Truth.assertThat(item.first().task.name).isEqualTo("whatever")
            Truth.assertThat(item.size).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testTaskPosition() = runTest {
        val task1 = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now(),
            isDone = false,
            position = 1,
        )
        val task2 = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now(),
            isDone = false,
            position = 2,
        )

        tasksDao.addTask(task2)
        tasksDao.addTask(task1)
        val result = tasksDao.observeAllTasks().first()

        Truth.assertThat(result.first().task.position).isEqualTo(1)
        Truth.assertThat(result.last().task.position).isEqualTo(2)
    }

    @Test
    fun testTaskUpdatePosition() = runTest {
        val task1 = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now(),
            isDone = false,
            position = 1,
        )
        val task2 = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now(),
            isDone = false,
            position = 2,
        )

        val task1Id = tasksDao.addTask(task1)
        val task2Id = tasksDao.addTask(task2)
        tasksDao.updateTaskPosition(task1Id, 3)
        val result = tasksDao.observeAllTasks().first()

        Truth.assertThat(result.last().task.id).isEqualTo(task1Id)
    }

    @Test
    fun testGetMaxPositionForDate() = runTest {
        val task1 = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now(),
            isDone = false,
            position = 1,
        )
        val task2 = TaskEntity(
            id = 0L,
            createdAt = LocalDate.now(),
            name = "whatever",
            day = LocalDate.now().plusDays(1),
            isDone = false,
            position = 2,
        )

        tasksDao.addTask(task1)
        tasksDao.addTask(task2)
        val result = tasksDao.getMaxPositionForDate(LocalDate.now())

        Truth.assertThat(result).isEqualTo(1)
    }
}
