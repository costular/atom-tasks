package com.costular.atomtasks.notifications

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.costular.atomtasks.core.ui.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TaskNotificationManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : TaskNotificationManager {

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return
        }

        val name = context.getString(R.string.notification_channel_reminders_title)
        val descriptionText =
            context.getString(R.string.notification_channel_reminders_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val reminders =
            NotificationChannel(NotificationChannels.Reminders, name, importance).apply {
                description = descriptionText
            }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(reminders)
    }

    override fun remindTask(taskId: Long, taskName: String) {
        val builder = buildNotificationBase(NotificationChannels.Reminders)
            .setContentTitle(context.getString(R.string.notification_reminder))
            .setContentText(taskName)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(taskName),
            )
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_OPEN_APP,
                    Intent().apply {
                        action = Intent.ACTION_VIEW
                        component = ComponentName(
                            context.packageName,
                            MAIN_ACTIVITY_NAME,
                        )
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    },
                    UPDATE_FLAG,
                    null,
                ),
            )
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(
                buildDoneAction(taskId),
            )
            .addAction(
                buildPostponeAction(taskId),
            )

        notify(taskId.toInt(), builder.build())
    }

    private fun buildPostponeAction(taskId: Long) = NotificationCompat.Action.Builder(
        0,
        context.getString(R.string.notification_reminder_postpone),
        PendingIntent.getActivity(
            context,
            generateRandomRequestCode(),
            Intent().apply {
                action = Intent.ACTION_VIEW
                component = ComponentName(
                    context.packageName,
                    POSTPONE_ACTIVITY_NAME,
                )
                putExtra(
                    "postpone_param_task_id",
                    taskId,
                )
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            },
            UPDATE_FLAG,
        )
    ).build()

    private fun buildDoneAction(taskId: Long) = NotificationCompat.Action.Builder(
        0,
        context.getString(R.string.notification_reminder_done),
        PendingIntent.getBroadcast(
            context,
            generateRandomRequestCode(),
            Intent().apply {
                action = Intent.ACTION_VIEW
                component = ComponentName(
                    context.packageName,
                    MARK_TASK_AS_DONE_RECEIVER,
                )
                putExtra(
                    "task_id",
                    taskId,
                )
            },
            UPDATE_FLAG,
        ),
    ).build()

    override fun removeTaskNotification(taskId: Long) {
        notificationManager.cancel(taskId.toInt())
    }

    @SuppressLint("MissingPermission")
    private fun notify(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    private fun buildNotificationBase(channel: String): NotificationCompat.Builder =
        NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_atom)
            .setColor(context.getColor(R.color.primary))

    private fun generateRandomRequestCode(): Int {
        return (0..Int.MAX_VALUE).random()
    }

    companion object {
        const val REQUEST_OPEN_APP = 20
        const val UPDATE_FLAG = FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        const val MAIN_ACTIVITY_NAME = "com.costular.atomtasks.ui.home.MainActivity"
        const val POSTPONE_ACTIVITY_NAME =
            "com.costular.atomtasks.postponetask.ui.PostponeTaskActivity"
        const val MARK_TASK_AS_DONE_RECEIVER =
            "com.costular.atomtasks.tasks.receiver.MarkTaskAsDoneReceiver"
    }
}
