package com.costular.atomreminders.data.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "created_at") val createdAt: LocalDate,
    val name: String,
    @ColumnInfo(name = "date") val day: LocalDate,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false
)