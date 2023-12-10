package com.costular.atomtasks.tasks.manager

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.costular.atomtasks.data.settings.IsAutoforwardTasksSettingEnabledUseCase
import com.costular.atomtasks.tasks.worker.AutoforwardTasksWorker
import com.costular.atomtasks.core.usecase.invoke
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Duration
import java.time.LocalTime
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class AutoforwardManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val isAutoforwardTasksSettingEnabledUseCase: IsAutoforwardTasksSettingEnabledUseCase,
) {
    suspend fun scheduleOrCancelAutoforwardTasks() {
        val isEnabled = isAutoforwardTasksSettingEnabledUseCase().first()

        if (isEnabled) {
            val delay = getDelayUntilAutoforward()
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

    private fun getDelayUntilAutoforward(): Duration {
        val now = LocalTime.now()
        val time = LocalTime.parse(TIME_FOR_AUTOFORWARD)

        return Duration.between(now, time).run {
            if (isNegative) {
                plusDays(1)
            } else {
                this
            }
        }
    }

    companion object {
        private const val TIME_FOR_AUTOFORWARD = "00:05"
    }
}
