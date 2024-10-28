package com.costular.atomtasks.data.settings.dailyreminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class DailyReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val request = OneTimeWorkRequestBuilder<DailyReminderWorker>()
            .build()
        WorkManager.getInstance(requireNotNull(context)).enqueue(request)
    }
}
