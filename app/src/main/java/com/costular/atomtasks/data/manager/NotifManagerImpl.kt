package com.costular.atomtasks.data.manager

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.costular.atomtasks.R
import com.costular.atomtasks.domain.manager.NotifManager
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.ui.util.CHANNEL_REMINDERS

class NotifManagerImpl(private val context: Context) : NotifManager {

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    override fun remindTask(task: Task) {
        val builder = buildNotificationBase(CHANNEL_REMINDERS)
            .setContentTitle(context.getString(R.string.notification_reminder))
            .setContentText(task.name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(task.name)
            )

        notify(task.id.toInt(), builder.build())
    }

    private fun notify(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    private fun buildNotificationBase(channel: String): NotificationCompat.Builder =
        NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.mipmap.ic_launcher)

}