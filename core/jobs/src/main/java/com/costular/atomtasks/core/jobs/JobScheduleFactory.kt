package com.costular.atomtasks.core.jobs

object JobScheduleFactory {
    fun jobCreator(jobScheduleFrequency: JobScheduleFrequency): JobCreator {
        return when (jobScheduleFrequency) {
            is JobScheduleFrequency.Daily -> DailyJobCreator(jobScheduleFrequency.time)
        }
    }
}
