package com.costular.atomtasks.postponetask.domain

import java.time.LocalDateTime

data class PostponeChoice(
    val postponeChoiceType: PostponeChoiceType,
    val postponeDateTime: LocalDateTime?,
)
