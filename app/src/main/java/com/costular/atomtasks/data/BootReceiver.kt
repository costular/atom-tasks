package com.costular.atomtasks.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.costular.atomtasks.tasks.worker.SetTasksRemindersWorker

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val request = OneTimeWorkRequestBuilder<SetTasksRemindersWorker>()
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }
}
