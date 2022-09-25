package com.costular.atomtasks.tasks.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.costular.atomtasks.tasks.worker.NotifyTaskWorker

class NotifyTaskReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val taskId = intent?.getLongExtra("task_id", -1L)

        if (taskId == -1L) {
            return
        }

        val request = OneTimeWorkRequestBuilder<NotifyTaskWorker>()
            .setInputData(workDataOf("task_id" to taskId))
            .build()

        WorkManager.getInstance(requireNotNull(context)).enqueue(request)
    }
}
