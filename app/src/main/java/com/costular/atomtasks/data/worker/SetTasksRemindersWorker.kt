package com.costular.atomtasks.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.domain.interactor.GetTasksWithReminderInteractor
import com.costular.atomtasks.domain.manager.ReminderManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.lang.Exception

@Suppress("TooGenericExceptionCaught")
@HiltWorker
class SetTasksRemindersWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTasksWithReminderInteractor: GetTasksWithReminderInteractor,
    private val reminderManager: ReminderManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        try {
            val tasks = getTasksWithReminderInteractor.executeSync(Unit)
            tasks.forEach { task ->
                if (task.reminder != null) {
                    reminderManager.set(task.id, task.reminder.localDateTime)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
}
