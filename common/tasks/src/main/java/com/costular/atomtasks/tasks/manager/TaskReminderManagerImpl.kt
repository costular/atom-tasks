package com.costular.atomtasks.tasks.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import com.costular.atomtasks.tasks.receiver.NotifyTaskReceiver
import java.time.LocalDateTime
import java.time.ZoneId

internal class TaskReminderManagerImpl(
    private val context: Context,
) : TaskReminderManager {

    private val alarmManager = context.getSystemService<AlarmManager>()

    override fun set(taskId: Long, localDateTime: LocalDateTime) {
        checkNotNull(alarmManager)

        AlarmManagerCompat.setExactAndAllowWhileIdle(
            alarmManager,
            AlarmManager.RTC_WAKEUP,
            localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            buildPendingIntent(taskId),
        )
    }

    override fun cancel(taskId: Long) {
        checkNotNull(alarmManager)
        val pendingIntent = buildPendingIntent(taskId)
        alarmManager.cancel(pendingIntent)
        pendingIntent.cancel()
    }

    private fun buildPendingIntent(taskId: Long): PendingIntent =
        PendingIntent.getBroadcast(
            context,
            taskId.toInt(),
            Intent(context, NotifyTaskReceiver::class.java).apply {
                putExtra("task_id", taskId)
            },
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            },
        )
}
