package com.costular.atomtasks.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.domain.interactor.GetTaskByIdInteractor
import com.costular.atomtasks.domain.manager.ErrorLogger
import com.costular.atomtasks.domain.manager.NotifManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@Suppress("TooGenericExceptionCaught")
@HiltWorker
class NotifyTaskWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTaskByIdInteractor: GetTaskByIdInteractor,
    private val notifManager: NotifManager,
    private val errorLogger: ErrorLogger,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1L)

        return try {
            if (taskId == -1L) {
                throw IllegalArgumentException("Task id has not been passed")
            }

            getTaskByIdInteractor(GetTaskByIdInteractor.Params(taskId))
            val task = getTaskByIdInteractor.flow.first()

            if (task.reminder == null) {
                throw IllegalStateException("Reminder is null")
            }

            if (!task.reminder.isToday || !task.reminder.isNow || !task.reminder.isEnabled) {
                throw IllegalStateException("Reminder is not valid")
            }

            if (task.isDone) {
                throw IllegalStateException(
                    "Reminder is done so does not makes sense to notify the reminder",
                )
            }

            notifManager.remindTask(task)
            Result.success()
        } catch (e: Exception) {
            errorLogger.logError(e)
            Result.failure()
        }
    }
}
