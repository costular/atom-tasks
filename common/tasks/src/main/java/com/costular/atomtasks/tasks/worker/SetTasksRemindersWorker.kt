package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.tasks.manager.ReminderManager
import com.costular.atomtasks.tasks.interactor.GetTasksWithReminderInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("TooGenericExceptionCaught", "SwallowedException")
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
                task.reminder?.let { reminder ->
                    reminderManager.set(task.id, reminder.localDateTime)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
}
