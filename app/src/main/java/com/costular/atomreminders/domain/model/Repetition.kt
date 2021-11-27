package com.costular.atomreminders.domain.model

import java.time.DayOfWeek

sealed class Repetition

object Daily : Repetition()

data class Weekly(val dayOfWeek: DayOfWeek) : Repetition()