package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.notifications.TaskNotificationManager
import com.costular.atomtasks.tasks.usecase.UpdateTaskIsDoneUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("TooGenericExceptionCaught", "SwallowedException")
@HiltWorker
class MarkTaskAsDoneWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val updateTaskIsDoneUseCase: UpdateTaskIsDoneUseCase,
    private val taskNotificationManager: TaskNotificationManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1L)

        return try {
            if (taskId == -1L) {
                throw IllegalArgumentException("Task id has not been passed")
            }
            taskNotificationManager.removeTaskNotification(taskId)

            updateTaskIsDoneUseCase.invoke(
                UpdateTaskIsDoneUseCase.Params(
                    taskId,
                    true,
                ),
            )

            Result.success()
        } catch (e: Exception) {
            atomLog { e }
            Result.failure()
        }
    }
}
