package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.costular.atomtasks.tasks.interactor.GetTasksWithReminderInteractor
import com.costular.atomtasks.tasks.manager.TaskReminderManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("TooGenericExceptionCaught", "SwallowedException")
@HiltWorker
class SetTasksRemindersWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTasksWithReminderInteractor: GetTasksWithReminderInteractor,
    private val taskReminderManager: TaskReminderManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result =
        try {
            val tasks = getTasksWithReminderInteractor.executeSync(Unit)
            tasks.forEach { task ->
                task.reminder?.let { reminder ->
                    taskReminderManager.set(task.id, reminder.localDateTime)
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }

    companion object {
        fun start() = OneTimeWorkRequestBuilder<SetTasksRemindersWorker>().build()
    }
}
