package com.costular.atomtasks.data.tasks

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import java.time.LocalTime

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminderEntity: ReminderEntity)

    @Query("SELECT EXISTS(SELECT * FROM reminders WHERE task_id = :taskId)")
    suspend fun reminderExistForTask(taskId: Long): Boolean

    @Query("UPDATE reminders SET time = :time WHERE task_id = :taskId")
    suspend fun updateReminder(taskId: Long, time: LocalTime)

    @Query("DELETE FROM reminders WHERE task_id = :taskId")
    suspend fun removeReminder(taskId: Long)

    @Update
    suspend fun update(reminderEntity: ReminderEntity)
}
