package com.costular.atomreminders.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.costular.atomreminders.data.habitrecord.HabitRecordDao
import com.costular.atomreminders.data.habitrecord.HabitRecordEntity
import com.costular.atomreminders.data.habits.HabitEntity
import com.costular.atomreminders.data.habits.HabitsDao
import com.costular.atomreminders.data.habits.ReminderDao
import com.costular.atomreminders.data.habits.ReminderEntity

@TypeConverters(DbTypeConverters::class)
@Database(
    entities = [HabitEntity::class, ReminderEntity::class, HabitRecordEntity::class],
    version = 4,
    exportSchema = true
)
abstract class AtomHabitDatabase : RoomDatabase() {

    abstract fun getHabitsDao(): HabitsDao
    abstract fun getRemindersDao(): ReminderDao
    abstract fun getHabitRecordDao(): HabitRecordDao

}