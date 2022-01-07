package com.costular.atomtasks.data.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.costular.atomtasks.data.worker.NotifyTaskWorker

class TaskNotificationService : Service() {

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val taskId = intent.getLongExtra("task_id", -1L)

        if (taskId == -1L) {
            return START_NOT_STICKY
        }

        val request = OneTimeWorkRequestBuilder<NotifyTaskWorker>()
            .setInputData(workDataOf("task_id" to taskId))
            .build()

        WorkManager.getInstance(applicationContext).enqueue(request)
        return START_STICKY
    }
}