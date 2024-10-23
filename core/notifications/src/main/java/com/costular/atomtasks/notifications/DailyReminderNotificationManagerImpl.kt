package com.costular.atomtasks.notifications

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.costular.atomtasks.core.ui.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DailyReminderNotificationManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : DailyReminderNotificationManager {

    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(context)
    }

    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val name = context.getString(R.string.notification_channel_daily_reminder)
        val descriptionText =
            context.getString(R.string.notification_channel_daily_reminder_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val dailyReminder =
            NotificationChannel(NotificationChannels.DailyReminder, name, importance).apply {
                description = descriptionText
            }

        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(dailyReminder)
    }

    @SuppressLint("MissingPermission")
    override fun showDailyReminderNotification() {
        val builder = context.buildNotificationBase(NotificationChannels.DailyReminder)
            .setContentTitle(context.getString(R.string.notification_daily_reminder_title))
            .setContentText(context.getString(R.string.notification_daily_reminder_description))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(context.getString(R.string.notification_daily_reminder_description)),
            )
            .openAppContentIntent(context)

        notificationManager.notify(DAILY_REMINDER_NOTIFICATION_ID, builder.build())
    }

    override fun removeDailyReminderNotification() {
        notificationManager.cancel(DAILY_REMINDER_NOTIFICATION_ID)
    }

    private companion object {
        const val DAILY_REMINDER_NOTIFICATION_ID = 9999990
    }
}