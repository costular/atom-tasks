package com.costular.atomhabits.data.habits

import androidx.room.Embedded
import androidx.room.Relation
import com.costular.atomhabits.data.habitrecord.HabitRecordEntity

data class HabitAggregrated(
    @Embedded val habit: HabitEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id"
    )
    val reminder: ReminderEntity?,
    @Relation(
        parentColumn = "id",
        entityColumn = "habit_id"
    )
    val records: List<HabitRecordEntity>
)