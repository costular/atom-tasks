package com.costular.atomreminders.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.costular.atomreminders.data.tasks.TaskEntity
import com.costular.atomreminders.data.tasks.TasksDao
import com.costular.atomreminders.data.tasks.ReminderDao
import com.costular.atomreminders.data.tasks.ReminderEntity

@TypeConverters(DbTypeConverters::class)
@Database(
    entities = [TaskEntity::class, ReminderEntity::class],
    version = 4,
    exportSchema = true
)
abstract class AtomRemindersDatabase : RoomDatabase() {

    abstract fun getTasksDao(): TasksDao
    abstract fun getRemindersDao(): ReminderDao

}