package com.costular.atomtasks.data.tasks

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "tasks",
    indices = [
        Index(
            value = ["created_at"],
            unique = false,
        ),
        Index(
            value = ["position", "date"],
            unique = true,
        ),
        Index(
            value = ["date"],
            unique = false,
        ),
    ],
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "created_at") val createdAt: LocalDate,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "date") val day: LocalDate,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false,
    @ColumnInfo(name = "position", defaultValue = "0") val position: Int = 0,
)
