package com.costular.atomtasks.core.jobs

import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequest
import java.time.LocalTime

interface JobCreator {
    suspend fun create(
        workerBuilder: OneTimeWorkRequest.Builder,
    ): OneTimeWorkRequest
}
