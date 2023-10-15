package com.costular.atomtasks.data.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(
    tableName = "reminders",
)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reminder_id") val reminderId: Long,
    @ColumnInfo(name = "time") val time: LocalTime,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "task_id") val taskId: Long,
)
