package com.costular.atomtasks.notifications

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.costular.atomtasks.core.ui.R

internal fun Context.buildNotificationBase(channel: String): NotificationCompat.Builder =
    NotificationCompat.Builder(this, channel)
        .setSmallIcon(R.drawable.ic_minitask)
        .setColor(this.getColor(R.color.primary))

internal fun generateRandomRequestCode(): Int {
    return (0..Int.MAX_VALUE).random()
}

internal fun NotificationCompat.Builder.openAppContentIntent(
    context: Context
): NotificationCompat.Builder {
    return setContentIntent(
        PendingIntent.getActivity(
            context,
            RequestOpenApp,
            Intent().apply {
                action = Intent.ACTION_VIEW
                component = ComponentName(
                    context.packageName,
                    MainActivityName,
                )
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            },
            UpdateFlag,
            null,
        ),
    )
}

internal const val UpdateFlag = FLAG_IMMUTABLE or FLAG_UPDATE_CURRENT
private const val RequestOpenApp = 20
private const val MainActivityName = "com.costular.atomtasks.ui.home.MainActivity"
