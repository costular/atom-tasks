package com.costular.atomtasks.tasks.helper

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.costular.atomtasks.core.usecase.invoke
import com.costular.atomtasks.core.util.getDelayUntil
import com.costular.atomtasks.data.settings.IsAutoforwardTasksSettingEnabledUseCase
import com.costular.atomtasks.tasks.worker.AutoforwardTasksWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.time.LocalTime
import javax.inject.Inject

class AutoforwardManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val isAutoforwardTasksSettingEnabledUseCase: IsAutoforwardTasksSettingEnabledUseCase,
) {
    suspend fun scheduleOrCancelAutoforwardTasks() {
        val isEnabled = isAutoforwardTasksSettingEnabledUseCase().first()

        if (isEnabled) {
            val delay = getDelayUntil(LocalTime.parse(TIME_FOR_AUTOFORWARD))
            val worker = AutoforwardTasksWorker.setUp(delay)

            WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                    "autoforward-tasks",
                    ExistingPeriodicWorkPolicy.UPDATE,
                    worker,
                )
        } else {
            cancelAutoforwardTasks()
        }
    }

    private fun cancelAutoforwardTasks() {
        WorkManager.getInstance(context).cancelAllWorkByTag(AutoforwardTasksWorker.TAG)
    }

    companion object {
        private const val TIME_FOR_AUTOFORWARD = "00:05"
    }
}
