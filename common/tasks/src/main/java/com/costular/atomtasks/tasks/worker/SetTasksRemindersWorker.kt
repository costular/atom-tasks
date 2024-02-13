package com.costular.atomtasks.tasks.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.costular.atomtasks.core.logging.atomLog
import com.costular.atomtasks.tasks.helper.TaskReminderManager
import com.costular.atomtasks.tasks.usecase.ObserveTasksUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@Suppress("TooGenericExceptionCaught", "SwallowedException")
@HiltWorker
class SetTasksRemindersWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val observeTasksUseCase: ObserveTasksUseCase,
    private val taskReminderManager: TaskReminderManager,
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = try {
        observeTasksUseCase(ObserveTasksUseCase.Params(day = null))
            .first()
            .tap { tasks ->
                tasks
                    .filter { it.reminder != null }
                    .forEach { task ->
                        task.reminder?.let { reminder ->
                            taskReminderManager.set(task.id, reminder.localDateTime)
                        }
                    }
            }
        Result.success()
    } catch (e: Exception) {
        atomLog { e }
        Result.failure()
    }

    companion object {
        fun start() = OneTimeWorkRequestBuilder<SetTasksRemindersWorker>().build()
    }
}
