package com.costular.atomreminders.ui.components.create_task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
internal fun TaskReminderData(
    reminder: LocalTime,
    onSelectReminder: (LocalTime) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reminder")
    }
}