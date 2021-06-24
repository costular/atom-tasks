package com.costular.atomhabits.data

import com.costular.atomhabits.data.habits.HabitEntity
import com.costular.atomhabits.domain.model.Daily
import com.costular.atomhabits.domain.model.Habit
import com.costular.atomhabits.domain.model.Weekly
import java.lang.IllegalStateException
import java.time.DayOfWeek

fun HabitEntity.toDomain(): Habit {
    val repetition = when {
        repetitionType == HabitEntity.REPETITION_TYPE_DAILY -> Daily
        repetitionType == HabitEntity.REPETITION_TYPE_WEEKLY && dayOfWeek != null -> Weekly(
            DayOfWeek.of(dayOfWeek)
        )
        else -> throw IllegalStateException("Type $repetitionType is not supported")
    }

    return Habit(
        id,
        name,
        repetition,
        createdAt,
    )
}