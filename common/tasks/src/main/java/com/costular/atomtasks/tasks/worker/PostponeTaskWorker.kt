package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.costular.atomtasks.tasks.manager.TaskNotificationManager
import com.costular.atomtasks.tasks.manager.TaskReminderManager
import com.costular.atomtasks.tasks.interactor.GetTaskByIdInteractor
import com.costular.atomtasks.tasks.interactor.UpdateTaskReminderInteractor
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.coroutines.flow.first

@Suppress("TooGenericExceptionCaught", "SwallowedException")
@HiltWorker
class PostponeTaskWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getTaskByIdInteractor: GetTaskByIdInteractor,
    private val updateTaskReminderInteractor: UpdateTaskReminderInteractor,
    private val taskNotificationManager: TaskNotificationManager,
    private val taskReminderManager: TaskReminderManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val taskId = inputData.getLong("task_id", -1L)

        return try {
            if (taskId == -1L) {
                throw IllegalArgumentException("Task id has not been passed")
            }
            taskNotificationManager.removeTaskNotification(taskId)

            getTaskByIdInteractor(GetTaskByIdInteractor.Params(taskId))
            val task = getTaskByIdInteractor.flow.first()

            if (task.reminder == null || (task.reminder?.isEnabled == false)) {
                throw IllegalStateException("Task has no active reminder")
            }

            val reminderTime = LocalTime.now().plusHours(1)

            updateTaskReminderInteractor(
                UpdateTaskReminderInteractor.Params(
                    taskId,
                    reminderTime,
                    LocalDate.now(),
                ),
            )
            taskReminderManager.set(task.id, reminderTime.atDate(LocalDate.now()))
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
