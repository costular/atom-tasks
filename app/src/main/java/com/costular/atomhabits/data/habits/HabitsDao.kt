package com.costular.atomhabits.data.habits

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addHabit(habitEntity: HabitEntity)

    @Query("SELECT * FROM habits")
    abstract fun getAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE repetition_type == '${HabitEntity.REPETITION_TYPE_DAILY}' OR (repetition_type == '${HabitEntity.REPETITION_TYPE_WEEKLY}' AND day_of_week == :dayOfWeek)")
    abstract fun getAllHabitsForDay(dayOfWeek: Int): Flow<List<HabitEntity>>

}