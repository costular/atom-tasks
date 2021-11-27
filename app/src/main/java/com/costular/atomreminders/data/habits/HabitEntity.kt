package com.costular.atomreminders.data.habits

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "created_at") val createdAt: LocalDate,
    val name: String,
    @ColumnInfo(name = "repetition_type") val repetitionType: String,
    @ColumnInfo(name = "day_of_week") val dayOfWeek: Int?
) {

    companion object {
        const val REPETITION_TYPE_DAILY = "daily"
        const val REPETITION_TYPE_WEEKLY = "weekly"
    }

}