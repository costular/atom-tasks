package com.costular.atomtasks.data.settings.dailyreminder

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.core.usecase.EmptyParams
import com.costular.atomtasks.notifications.DailyReminderNotificationManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DailyReminderWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val dailyReminderNotificationManager: DailyReminderNotificationManager,
    private val syncDailyReminderUseCase: SyncDailyReminderUseCase,
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            dailyReminderNotificationManager.showDailyReminderNotification()
            // Re-schedule future daily reminders
            syncDailyReminderUseCase.invoke(EmptyParams)
            Result.success()
        } catch (e: Exception) {
            atomLog { e }
            Result.failure()
        }
    }
}
