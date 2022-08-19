package com.costular.atomtasks.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.data.tasks.UpdateTaskIsDoneInteractor
import com.costular.atomtasks.data.manager.NotifManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("TooGenericExceptionCaught")
@HiltWorker
class MarkTaskAsDoneWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor,
    private val notifManager: NotifManager,
    private val errorLogger: ErrorLogger,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1L)

        return try {
            if (taskId == -1L) {
                throw IllegalArgumentException("Task id has not been passed")
            }
            notifManager.removeTaskNotification(taskId)

            updateTaskIsDoneInteractor.executeSync(
                UpdateTaskIsDoneInteractor.Params(
                    taskId,
                    true,
                ),
            )

            Result.success()
        } catch (e: Exception) {
            errorLogger.logError(e)
            Result.failure()
        }
    }
}
