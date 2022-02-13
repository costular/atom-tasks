package com.costular.atomtasks.data.tasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.LocalTime

@Dao
abstract class ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertReminder(reminderEntity: ReminderEntity)

    @Query("UPDATE reminders SET time = :time WHERE task_id = :taskId")
    abstract suspend fun updateReminder(taskId: Long, time: LocalTime)

    @Query("DELETE FROM reminders WHERE task_id = :taskId")
    abstract suspend fun removeReminder(taskId: Long)
}
