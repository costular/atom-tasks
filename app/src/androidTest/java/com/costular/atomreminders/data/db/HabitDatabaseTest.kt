package com.costular.atomreminders.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.costular.atomreminders.data.habitrecord.HabitRecordDao
import com.costular.atomreminders.data.habitrecord.HabitRecordEntity
import com.costular.atomreminders.data.habits.HabitEntity
import com.costular.atomreminders.data.habits.HabitsDao
import com.costular.atomreminders.db.AtomHabitDatabase
import com.google.common.truth.Truth
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

@ExperimentalTime
@RunWith(AndroidJUnit4::class)
class HabitDatabaseTest {

    private val testCoroutine: TestCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var db: AtomHabitDatabase
    private lateinit var habitsDao: HabitsDao
    private lateinit var habitRecordDao: HabitRecordDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AtomHabitDatabase::class.java
        )
            .setTransactionExecutor(testCoroutine.asExecutor())
            .setQueryExecutor(testCoroutine.asExecutor())
            .allowMainThreadQueries()
            .build()
        habitsDao = db.getHabitsDao()
        habitRecordDao = db.getHabitRecordDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        db.close()
    }

    @Test
    fun testAddHabit() = testCoroutine.runBlockingTest {
        // Given
        val habit = HabitEntity(
            0L,
            LocalDate.now(),
            "whatever",
            HabitEntity.REPETITION_TYPE_DAILY,
            null
        )

        // When
        val id = habitsDao.addHabit(habit)

        // Then
        habitsDao.getAllHabits().test {
            Truth.assertThat(expectItem().size).isEqualTo(1)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun testAddHabitRecord() = testCoroutine.runBlockingTest {
        // Given
        val habit = HabitEntity(
            0L,
            LocalDate.now(),
            "whatever",
            HabitEntity.REPETITION_TYPE_DAILY,
            null
        )

        // When
        val id = habitsDao.addHabit(habit)

        val records = listOf(
            HabitRecordEntity(
                0L,
                LocalDate.now(),
                id
            ),
            HabitRecordEntity(
                0L,
                LocalDate.now().plusDays(1),
                id
            )
        )
        records.forEach { record ->
            habitRecordDao.addEntry(record)
        }

        // Then
        habitsDao.getAllHabits().test {
            val habit = expectItem()
            Truth.assertThat(habit.size).isEqualTo(1)
            Truth.assertThat(habit.first().records.size).isEqualTo(records.size)
        }
    }

}