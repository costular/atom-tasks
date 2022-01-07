package com.costular.atomtasks.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.domain.interactor.GetTaskByIdInteractor
import com.costular.atomtasks.domain.manager.NotifManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import java.lang.Exception
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

@HiltWorker
class NotifyTaskWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTaskByIdInteractor: GetTaskByIdInteractor,
    private val notifManager: NotifManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1L)

        return try {
            if (taskId == -1L) {
                throw IllegalArgumentException()
            }

            getTaskByIdInteractor(GetTaskByIdInteractor.Params(taskId))
            val task = getTaskByIdInteractor.observe().first()

            if (task.reminder == null) {
                throw IllegalStateException()
            }

            if (!task.reminder.isToday || !task.reminder.isNow || !task.reminder.isEnabled) {
                throw IllegalStateException()
            }
            notifManager.remindTask(task)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

}