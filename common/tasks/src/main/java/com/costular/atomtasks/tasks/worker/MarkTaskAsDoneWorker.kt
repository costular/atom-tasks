package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.notifications.TaskNotificationManager
import com.costular.atomtasks.tasks.interactor.UpdateTaskIsDoneInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("TooGenericExceptionCaught", "SwallowedException")
@HiltWorker
class MarkTaskAsDoneWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateTaskIsDoneInteractor: UpdateTaskIsDoneInteractor,
    private val taskNotificationManager: TaskNotificationManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1L)

        return try {
            if (taskId == -1L) {
                throw IllegalArgumentException("Task id has not been passed")
            }
            taskNotificationManager.removeTaskNotification(taskId)

            updateTaskIsDoneInteractor.executeSync(
                UpdateTaskIsDoneInteractor.Params(
                    taskId,
                    true,
                ),
            )

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
