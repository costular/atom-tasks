package com.costular.atomtasks.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec
import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao

@TypeConverters(DbTypeConverters::class)
@Database(
    entities = [TaskEntity::class, ReminderEntity::class],
    version = 6,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 5,
            to = 6,
            spec = MigrationDeleteIsEnabledReminder::class,
        )
    ]
)
abstract class AtomTasksDatabase : RoomDatabase() {
    abstract fun getTasksDao(): TasksDao
    abstract fun getRemindersDao(): ReminderDao
}
