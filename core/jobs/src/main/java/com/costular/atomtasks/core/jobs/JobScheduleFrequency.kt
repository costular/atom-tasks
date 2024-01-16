package com.costular.atomtasks.core.jobs

import java.time.LocalTime

sealed class JobScheduleFrequency(
    open val time: LocalTime
) {
    data class Daily(override val time: LocalTime) : JobScheduleFrequency(time)
}

fun daily(time: LocalTime): JobScheduleFrequency = JobScheduleFrequency.Daily(time)
