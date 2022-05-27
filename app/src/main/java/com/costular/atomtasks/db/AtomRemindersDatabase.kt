package com.costular.atomtasks.db

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
    version = 4,
    exportSchema = true,
)
abstract class AtomRemindersDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDao
    abstract fun getRemindersDao(): ReminderDao
}
