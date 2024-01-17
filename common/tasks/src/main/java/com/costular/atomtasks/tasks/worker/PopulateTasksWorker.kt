package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.tasks.helper.recurrence.RecurrenceManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate

class PopulateTasksWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val recurrenceManager: RecurrenceManager,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            recurrenceManager.createAheadTasks(LocalDate.now())
            Result.success()
        } catch (e: Exception) {
            atomLog { e }
            Result.failure()
        }
    }
}
