package com.costular.atomreminders.data.habits

import androidx.room.Embedded
import androidx.room.Relation
import com.costular.atomreminders.data.habitrecord.HabitRecordEntity

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