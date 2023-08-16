package com.costular.atomtasks.tasks.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.costular.atomtasks.tasks.worker.AutoforwardTasksWorker
import java.util.concurrent.TimeUnit

class EnqueueMarkUndoneTaskReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        checkNotNull(context)

        val request = PeriodicWorkRequestBuilder<AutoforwardTasksWorker>(
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS,
        ).build()

        WorkManager.getInstance(context).enqueue(request)
    }
}
