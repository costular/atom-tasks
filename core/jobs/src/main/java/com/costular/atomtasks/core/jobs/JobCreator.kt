package com.costular.atomtasks.core.jobs

import androidx.work.OneTimeWorkRequest

interface JobCreator {
    suspend fun create(
        workerBuilder: OneTimeWorkRequest.Builder,
    ): OneTimeWorkRequest
}
