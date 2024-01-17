package com.costular.atomtasks.core.jobs

import androidx.work.OneTimeWorkRequest
import java.time.Duration
import java.time.LocalTime

internal class DailyJobCreator(
    private val time: LocalTime
) : JobCreator {
    override suspend fun create(
        workerBuilder: OneTimeWorkRequest.Builder,
    ): OneTimeWorkRequest {
        return workerBuilder
            .setInitialDelay(getDelayUntilAutoforward())
            .build()
    }

    private fun getDelayUntilAutoforward(): Duration {
        val now = LocalTime.now()

        return Duration.between(now, time).run {
            if (isNegative) {
                plusDays(1)
            } else {
                this
            }
        }
    }
}
