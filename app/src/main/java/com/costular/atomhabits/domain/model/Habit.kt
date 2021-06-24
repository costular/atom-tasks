package com.costular.atomhabits.domain.model

import java.time.LocalDate
import kotlin.random.Random

data class Habit(
    val id: Long,
    val name: String,
    val repetition: Repetition,
    val createdAt: LocalDate
) {
    val isFinishedForToday: Boolean = Random.nextBoolean() // TODO: 22/6/21 do not hardcode this
}