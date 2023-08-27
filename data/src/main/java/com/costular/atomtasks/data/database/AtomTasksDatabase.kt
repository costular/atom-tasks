package com.costular.atomtasks.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.costular.atomtasks.data.tasks.ReminderDao
import com.costular.atomtasks.data.tasks.ReminderEntity
import com.costular.atomtasks.data.tasks.TaskEntity
import com.costular.atomtasks.data.tasks.TasksDao

@TypeConverters(DbTypeConverters::class)
@Database(
    entities = [TaskEntity::class, ReminderEntity::class],
    version = 5,
    exportSchema = true,
)
abstract class AtomTasksDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDao
    abstract fun getRemindersDao(): ReminderDao
}
