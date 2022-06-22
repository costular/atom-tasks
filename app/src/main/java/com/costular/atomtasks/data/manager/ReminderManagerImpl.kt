package com.costular.atomtasks.data.manager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.AlarmManagerCompat
import androidx.core.content.getSystemService
import com.costular.atomtasks.data.receiver.NotifyTaskReceiver
import com.costular.atomtasks.domain.manager.ReminderManager
import java.time.LocalDateTime
import java.time.ZoneId

class ReminderManagerImpl(
    private val context: Context,
) : ReminderManager {

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
            PendingIntent.FLAG_UPDATE_CURRENT,
        )
}
