package com.costular.atomtasks.data

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.costular.atomtasks.tasks.Task
import com.costular.atomtasks.tasks.receiver.PostponeTaskReceiver
import com.costular.atomtasks.tasks.manager.TaskNotificationManager
import com.costular.atomtasks.tasks.receiver.MarkTaskAsDoneReceiver
import com.costular.atomtasks.ui.home.MainActivity
import com.costular.atomtasks.ui.util.ChannelReminders

class TaskNotificationManagerImpl(
    private val context: Context,
) : TaskNotificationManager {

    private val notificationManager: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_reminders_title)
            val descriptionText =
                context.getString(R.string.notification_channel_reminders_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val reminders = NotificationChannel(ChannelReminders, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(reminders)
        }
    }

    override fun remindTask(task: Task) {
        val pendingIntentFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
        } else {
            FLAG_UPDATE_CURRENT
        }

        val builder = buildNotificationBase(ChannelReminders)
            .setContentTitle(task.name)
            .setContentText(context.getString(R.string.notification_reminder))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(task.name),
            )
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_OPEN_APP,
                    Intent(context, MainActivity::class.java),
                    pendingIntentFlag,
                    null,
                ),
            )
            .setAutoCancel(true)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .addAction(
                buildDoneAction(task, pendingIntentFlag),
            )
            .addAction(
                buildPostponeAction(task, pendingIntentFlag),
            )

        notify(task.id.toInt(), builder.build())
    }

    private fun buildPostponeAction(
        task: Task,
        pendingIntentFlag: Int,
    ) = NotificationCompat.Action.Builder(
        0,
        context.getString(R.string.notification_reminder_postpone),
        PendingIntent.getBroadcast(
            context,
            generateRandomRequestCode(),
            Intent(context, PostponeTaskReceiver::class.java).apply {
                putExtra(PostponeTaskReceiver.PARAM_TASK_ID, task.id)
            },
            pendingIntentFlag,
        ),
    ).build()

    private fun buildDoneAction(
        task: Task,
        pendingIntentFlag: Int,
    ) = NotificationCompat.Action.Builder(
        0,
        context.getString(R.string.notification_reminder_done),
        PendingIntent.getBroadcast(
            context,
            generateRandomRequestCode(),
            Intent(
                context,
                MarkTaskAsDoneReceiver::class.java,
            ).apply {
                putExtra(
                    MarkTaskAsDoneReceiver.PARAM_TASK_ID,
                    task.id,
                )
            },
            pendingIntentFlag,
        ),
    ).build()

    override fun removeTaskNotification(taskId: Long) {
        notificationManager.cancel(taskId.toInt())
    }

    private fun notify(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    private fun buildNotificationBase(channel: String): NotificationCompat.Builder =
        NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_atom)
            .setColor(R.color.primary)

    private fun generateRandomRequestCode(): Int {
        return (0..Int.MAX_VALUE).random()
    }

    companion object {
        const val REQUEST_OPEN_APP = 20
    }
}
