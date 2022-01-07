package com.costular.atomtasks.data.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.costular.atomtasks.data.worker.SetTasksRemindersWorker

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == BOOT) {
            val request = OneTimeWorkRequestBuilder<SetTasksRemindersWorker>()
                .build()
            WorkManager.getInstance(context).enqueue(request)
        }
    }

    companion object {
        const val BOOT = "android.intent.action.BOOT_COMPLETED"
    }

}