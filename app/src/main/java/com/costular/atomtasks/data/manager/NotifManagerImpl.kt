package com.costular.atomtasks.data.manager

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.costular.atomtasks.MainActivity
import com.costular.atomtasks.R
import com.costular.atomtasks.data.receiver.MarkTaskAsDoneReceiver
import com.costular.atomtasks.data.receiver.PostponeTaskReceiver
import com.costular.atomtasks.domain.manager.NotifManager
import com.costular.atomtasks.domain.model.Task
import com.costular.atomtasks.ui.theme.Teal500
import com.costular.atomtasks.ui.util.CHANNEL_REMINDERS

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
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
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_OPEN_APP,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT,
                    null,
                )
            )
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(
                NotificationCompat.Action.Builder(
                    0,
                    context.getString(R.string.notification_reminder_done),
                    PendingIntent.getBroadcast(
                        context,
                        REQUEST_ACTION_DONE,
                        Intent(context, MarkTaskAsDoneReceiver::class.java).apply {
                            putExtra(MarkTaskAsDoneReceiver.PARAM_TASK_ID, task.id)
                        },
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )
                ).build()
            )
            .addAction(
                NotificationCompat.Action.Builder(
                    0,
                    context.getString(R.string.notification_reminder_postpone),
                    PendingIntent.getBroadcast(
                        context,
                        REQUEST_ACTION_POSTPONE,
                        Intent(context, PostponeTaskReceiver::class.java).apply {
                            putExtra(PostponeTaskReceiver.PARAM_TASK_ID, task.id)
                        },
                        PendingIntent.FLAG_CANCEL_CURRENT
                    )
                ).build()
            )

        notify(task.id.toInt(), builder.build())
    }

    override fun removeTaskNotification(taskId: Long) {
        notificationManager.cancel(taskId.toInt())
    }

    private fun notify(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    private fun buildNotificationBase(channel: String): NotificationCompat.Builder =
        NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_atom)
            .setColor(Teal500.toArgb())

    companion object {
        const val REQUEST_OPEN_APP = 20
        const val REQUEST_ACTION_DONE = 21
        const val REQUEST_ACTION_POSTPONE = 22
    }

}