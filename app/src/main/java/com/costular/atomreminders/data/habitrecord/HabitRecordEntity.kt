package com.costular.atomreminders.data.habitrecord

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "habit_entries",
    indices = [
        Index(value = ["date"], unique = true)
    ]
)
data class HabitRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val date: LocalDate,
    @ColumnInfo(name = "habit_id") val habitId: Long
)