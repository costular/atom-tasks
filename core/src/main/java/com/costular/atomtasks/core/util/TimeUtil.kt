package com.costular.atomtasks.core.util

import java.time.Duration
import java.time.LocalTime

fun getDelayUntil(time: LocalTime): Duration {
    val now = LocalTime.now()

    return Duration.between(now, time).run {
        if (isNegative) {
            plusDays(1)
        } else {
            this
        }
    }
}
