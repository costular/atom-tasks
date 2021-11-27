package com.costular.atomreminders.data.habitrecord

import androidx.room.*
import java.time.LocalDate

@Dao
abstract class HabitRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addEntry(habitRecordEntity: HabitRecordEntity)

    @Query("DELETE FROM habit_entries WHERE date = :date AND habit_id == :habitId")
    abstract suspend fun deleteEntryByDate(habitId: Long, date: LocalDate)

}