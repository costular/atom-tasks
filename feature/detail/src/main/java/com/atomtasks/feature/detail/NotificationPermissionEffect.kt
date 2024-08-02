package com.atomtasks.feature.detail

import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION_CODES.S
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.core.content.getSystemService
import com.costular.atomtasks.tasks.createtask.NotificationRationale
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun NotificationPermissionEffect(
    onRevoke: () -> Unit,
    onAccept: @Composable () -> Unit,
) {
    if (LocalInspectionMode.current) return
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
        onAccept()
        return
    }

    val context = LocalContext.current
    val alarmManager = context.applicationContext.getSystemService<AlarmManager>()

    val notificationsPermissionState = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS,
    )

    if (notificationsPermissionState.status.isGranted) {
        onAccept()
    }

    if (notificationsPermissionState.status.shouldShowRationale) {
        NotificationRationale(
            onDismiss = onRevoke,
            onAccept = {
                notificationsPermissionState.launchPermissionRequest()
            }
        )
    }

    checkNotNull(alarmManager)
    if (Build.VERSION.SDK_INT >= S && !alarmManager.canScheduleExactAlarms()) {
        val intent = Intent(ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
        intent.setData(Uri.fromParts("package", context.packageName, null))
        context.startActivity(intent)
    }

    LaunchedEffect(notificationsPermissionState) {
        val status = notificationsPermissionState.status
        when {
            status is PermissionStatus.Denied && !status.shouldShowRationale -> {
                notificationsPermissionState.launchPermissionRequest()
            }
        }
    }
}
