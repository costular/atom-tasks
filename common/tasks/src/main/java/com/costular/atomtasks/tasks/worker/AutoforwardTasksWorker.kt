package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.tasks.interactor.AutoforwardTasksUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Duration
import java.time.LocalDate

@HiltWorker
class AutoforwardTasksWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val autoforwardTasksUseCase: AutoforwardTasksUseCase,
) : CoroutineWorker(appContext, workerParams) {

    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    override suspend fun doWork(): Result {
        return try {
            autoforwardTasksUseCase(AutoforwardTasksUseCase.Params(LocalDate.now()))
            Result.success()
        } catch (e: Exception) {
            atomLog { e }
            Result.failure()
        }
    }

    companion object {
        private const val REPEAT_HOURS = 24L
        const val TAG = "autoforward_worker"

        fun setUp(initialDelay: Duration): PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<AutoforwardTasksWorker>(
                Duration.ofHours(REPEAT_HOURS),
            )
                .setInitialDelay(initialDelay)
                .addTag(TAG)
                .build()
    }
}
