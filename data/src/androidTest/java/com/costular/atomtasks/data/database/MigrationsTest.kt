package com.costular.atomtasks.data.database

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth
import java.io.IOException
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MigrationsTest {
    private val ALL_MIGRATIONS = arrayOf(MIGRATION_4_5)
    private val TEST_DB = "migration-test"
    private val LATEST_VERSION = 7

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
        val db = helper.createDatabase(TEST_DB, 4).apply {
            execSQL(
                "INSERT INTO tasks (created_at, name, date, is_done) VALUES " +
                        "('2023-08-23', 'This is a test', '2023-08-23', 0), " +
                        "('2023-08-24', 'First task for this day', '2023-08-24', 0), " +
                        "('2023-08-23', 'Second task', '2023-08-23', 0);",
            )
        }

        helper.runMigrationsAndValidate(TEST_DB, 5, true, MIGRATION_4_5)

        val cursor = db.query("SELECT * FROM tasks", arrayOf())

        if (cursor.moveToFirst()) {
            val positionColumnIndex = cursor.getColumnIndex("position")

            if (positionColumnIndex != -1) {
                val positions = mutableListOf<Int>()

                do {
                    val position = cursor.getInt(positionColumnIndex)
                    positions.add(position)
                } while (cursor.moveToNext())

                Truth.assertThat(positions[0]).isEqualTo(1)
                Truth.assertThat(positions[1]).isEqualTo(2)
                Truth.assertThat(positions[2]).isEqualTo(3)
            }
        }
        cursor.close()
    }

    @Test
    @Throws(IOException::class)
    fun testMigration5To6() {
        val db = helper.createDatabase(TEST_DB, 5).apply {
            execSQL(
                "INSERT INTO tasks (created_at, name, date, is_done) VALUES " +
                        "('2023-08-23', 'This is a test', '2023-08-23', 0); "
            )
            execSQL(
                "INSERT INTO reminders (reminder_id, time, date, is_enabled, task_id) VALUES " +
                        "(1, '09:00', '2023-10-15', 1, 1)"
            )
        }

        helper.runMigrationsAndValidate(TEST_DB, 6, true)

        val cursor = db.query("SELECT * FROM reminders", arrayOf())

        if (cursor.moveToFirst()) {
            val positionColumnIndex = cursor.getColumnIndex("time")

            if (positionColumnIndex != -1) {
                val reminderTimes = mutableListOf<String>()

                do {
                    val reminderTime = cursor.getString(positionColumnIndex)
                    reminderTimes.add(reminderTime)
                } while (cursor.moveToNext())

                Truth.assertThat(reminderTimes.first()).isEqualTo("09:00")
            }
        }
        cursor.close()
    }

    @Test
    @Throws(IOException::class)
    fun testMigration6To7() {
        val db = helper.createDatabase(TEST_DB, 6).apply {
            execSQL(
                "INSERT INTO tasks (created_at, name, date, is_done) VALUES " +
                        "('2023-08-23', 'This is a test', '2023-08-23', 0); "
            )
        }

        helper.runMigrationsAndValidate(TEST_DB, 7, true)

        val cursor = db.query("SELECT * FROM tasks", arrayOf())

        if (cursor.moveToFirst()) {
            val positionColumnIndex = cursor.getColumnIndex("name")

            if (positionColumnIndex != -1) {
                cursor.moveToFirst()
                val name = cursor.getString(positionColumnIndex)
                Truth.assertThat(name).isEqualTo("This is a test")
            }
        }
        cursor.close()
    }
}
