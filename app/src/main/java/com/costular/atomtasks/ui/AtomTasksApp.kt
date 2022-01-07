package com.costular.atomtasks.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.costular.atomtasks.BuildConfig
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.util.CHANNEL_REMINDERS
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AtomTasksApp : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
        createNotificationChannels()
    }

    private fun init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.notification_channel_reminders_title)
            val descriptionText = getString(R.string.notification_channel_reminders_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val reminders = NotificationChannel(CHANNEL_REMINDERS, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(reminders)
        }
    }

}