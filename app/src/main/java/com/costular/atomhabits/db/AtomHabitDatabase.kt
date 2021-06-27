package com.costular.atomhabits.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.costular.atomhabits.data.habitrecord.HabitRecordDao
import com.costular.atomhabits.data.habitrecord.HabitRecordEntity
import com.costular.atomhabits.data.habits.HabitEntity
import com.costular.atomhabits.data.habits.HabitsDao
import com.costular.atomhabits.data.habits.ReminderDao
import com.costular.atomhabits.data.habits.ReminderEntity

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