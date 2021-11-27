package com.costular.atomreminders.data.habits

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addHabit(habitEntity: HabitEntity): Long

    @Query("SELECT * FROM habits")
    abstract fun getAllHabits(): Flow<List<HabitAggregrated>>

    @Transaction
    @Query("SELECT * FROM habits WHERE repetition_type == '${HabitEntity.REPETITION_TYPE_DAILY}' OR (repetition_type == '${HabitEntity.REPETITION_TYPE_WEEKLY}' AND day_of_week == :dayOfWeek)")
    abstract fun getAllHabitsForDay(dayOfWeek: Int): Flow<List<HabitAggregrated>>

    @Transaction
    @Query("SELECT * FROM habits WHERE id = :id LIMIT 1")
    abstract fun getHabitById(id: Long): Flow<HabitAggregrated>

    @Query("DELETE FROM habits WHERE id = :id")
    abstract suspend fun removeHabitById(id: Long)

}