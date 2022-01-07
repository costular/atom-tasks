package com.costular.atomtasks.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.domain.interactor.GetTasksWithReminderInteractor
import com.costular.atomtasks.domain.manager.ReminderManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.single
import java.lang.Exception

@HiltWorker
class SetTasksRemindersWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTasksWithReminderInteractor: GetTasksWithReminderInteractor,
    private val reminderManager: ReminderManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        try {
            val tasks = getTasksWithReminderInteractor(Unit).single()
            tasks.forEach { task ->
                if (task.reminder != null) {
                    reminderManager.set(task.id, task.reminder.time.atDate(task.reminder.date))
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }


}