package com.costular.atomtasks.tasks.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.costular.atomtasks.analytics.AtomAnalytics
import com.costular.atomtasks.tasks.analytics.NotificationsActionsPostpone
import com.costular.atomtasks.tasks.worker.PostponeTaskWorker
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PostponeTaskReceiver : BroadcastReceiver() {

    @Inject
    lateinit var atomAnalytics: AtomAnalytics

    override fun onReceive(context: Context?, intent: Intent?) {
        val taskId = intent?.getLongExtra(PARAM_TASK_ID, -1L)

        if (taskId == -1L) {
            return
        }

        val request = OneTimeWorkRequestBuilder<PostponeTaskWorker>()
            .setInputData(workDataOf("task_id" to taskId))
            .build()

        WorkManager.getInstance(requireNotNull(context))
            .enqueue(request)

        atomAnalytics.track(NotificationsActionsPostpone)
    }

    companion object {
        const val PARAM_TASK_ID = "task_id"
    }
}
