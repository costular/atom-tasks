package com.costular.atomhabits.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.costular.atomhabits.data.habits.HabitEntity
import com.costular.atomhabits.data.habits.HabitsDao

@TypeConverters(DbTypeConverters::class)
@Database(entities = [HabitEntity::class], version = 1, exportSchema = true)
abstract class AtomHabitDatabase : RoomDatabase() {

    abstract fun getHabitsDao(): HabitsDao

}