package com.costular.atomreminders.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.costular.atomreminders.data.tasks.TaskEntity
import com.costular.atomreminders.data.tasks.TasksDao
import com.costular.atomreminders.db.AtomRemindersDatabase
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asExecutor
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.time.LocalDate
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class TaskDatabaseTest {

    private val testCoroutine: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var db: AtomRemindersDatabase
    private lateinit var tasksDao: TasksDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AtomRemindersDatabase::class.java
        )
            .setTransactionExecutor(testCoroutine.asExecutor())
            .setQueryExecutor(testCoroutine.asExecutor())
            .allowMainThreadQueries()
            .build()
        tasksDao = db.getHabitsDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAddHabit() = testCoroutine.runBlockingTest {
        // Given
        val habit = TaskEntity(
            0L,
            LocalDate.now(),
            "whatever",
            LocalDate.now(),
            true
        )

        // When
        val id = tasksDao.addTask(habit)

        // Then
        tasksDao.getAllTasks().test {
            Truth.assertThat(awaitItem().size).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

}