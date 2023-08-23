package com.costular.atomtasks.data.database

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import java.io.IOException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationsTest {
    private val ALL_MIGRATIONS = arrayOf(MIGRATION_4_5)
    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AtomTasksDatabase::class.java,
    )

    @Test
    @Throws(IOException::class)
    fun migrateAll() {
        helper.createDatabase(TEST_DB, 4).apply {
            execSQL(
                "INSERT INTO tasks (created_at, name, date, is_done) VALUES" +
                    " ('2023-08-23', 'This is a test', '2023-08-23', 0)",
            )
            close()
        }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AtomTasksDatabase::class.java,
            TEST_DB,
        ).addMigrations(*ALL_MIGRATIONS).build().apply {
            openHelper.writableDatabase.close()
        }

        helper.runMigrationsAndValidate(TEST_DB, LATEST_VERSION, true)
    }

    @Test
    fun testMigrate4To5() = runTest {
        helper.createDatabase(TEST_DB, 4).apply {
            execSQL(
                "INSERT INTO tasks (created_at, name, date, is_done) VALUES " +
                    "('2023-08-23', 'This is a test', '2023-08-23', 0), " +
                    "('2023-08-23', 'Second task', '2023-08-23', 0);",
            )
            close()
        }

        val db = Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            AtomTasksDatabase::class.java,
            TEST_DB,
        ).addMigrations(MIGRATION_4_5).build().apply {
            openHelper.writableDatabase.close()
        }
        helper.runMigrationsAndValidate(TEST_DB, 5, true)

        val tasks = db.getTasksDao().observeAllTasks().first()
        Truth.assertThat(tasks.first().task.position).isEqualTo(1)
        Truth.assertThat(tasks.last().task.position).isEqualTo(2)
    }

    private companion object {
        const val LATEST_VERSION = 5
    }
}
