package com.costular.atomhabits.data

import com.costular.atomhabits.data.habitrecord.HabitRecordEntity
import com.costular.atomhabits.data.habits.HabitEntity
import com.costular.atomhabits.data.habits.HabitAggregrated
import com.costular.atomhabits.data.habits.ReminderEntity
import com.costular.atomhabits.domain.model.*
import java.lang.IllegalStateException
import java.time.DayOfWeek

fun HabitAggregrated.toDomain(): Habit {
    val repetition = when {
        habit.repetitionType == HabitEntity.REPETITION_TYPE_DAILY -> Daily
        habit.repetitionType == HabitEntity.REPETITION_TYPE_WEEKLY && habit.dayOfWeek != null -> Weekly(
            DayOfWeek.of(habit.dayOfWeek)
        )
        else -> throw IllegalStateException("Type ${habit.repetitionType} is not supported")
    }

    return Habit(
        habit.id,
        habit.name,
        repetition,
        habit.createdAt,
        reminder?.toDomain(),
        records.map { it.toDomain() }
    )
}

fun HabitRecordEntity.toDomain(): HabitRecord = HabitRecord(id, date)

fun ReminderEntity.toDomain(): Reminder = Reminder(
    habitId,
    time,
    isEnabled,
    DayOfWeek.of(dayOfWeek)
)