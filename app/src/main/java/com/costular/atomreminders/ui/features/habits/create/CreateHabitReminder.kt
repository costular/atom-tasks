package com.costular.atomreminders.ui.features.habits.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import com.vanpra.composematerialdialogs.MaterialDialog
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.costular.atomreminders.R
import com.costular.atomreminders.ui.components.ScreenHeader
import com.costular.atomreminders.ui.util.DateTimeFormatters
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime

@Composable
fun CreateHabitReminder(
    state: CreateHabitState,
    onChangeReminderEnabled: (Boolean) -> Unit,
    onChangeReminderTime: (LocalTime) -> Unit
) {
    val dialogState = rememberMaterialDialogState()
    MaterialDialog(
        dialogState = dialogState,
        buttons = {
            positiveButton("Ok")
            negativeButton("Cancel")
        }
    ) {
        timepicker(initialTime = state.reminderTime) { time ->
            onChangeReminderTime(time)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenHeader(
                stringResource(R.string.create_habit_reminder_header),
                modifier = Modifier.padding(16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Enable reminder for this habit?",
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.weight(1f)
                )
                Switch(
                    checked = state.reminderEnabled,
                    onCheckedChange = {
                        onChangeReminderEnabled(it)
                    }
                )
            }

            if (state.reminderEnabled) {
                val interactionSource = remember { MutableInteractionSource() }

                Box {
                    OutlinedTextField(
                        value = DateTimeFormatters.timeFormatter.format(state.reminderTime),
                        label = null,
                        readOnly = true,
                        onValueChange = { },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Filled.ArrowDropDown,
                                contentDescription = null
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clickable(
                                onClick = { dialogState.show() },
                                interactionSource = interactionSource,
                                indication = null
                            )
                    )
                }

            }
        }
    }

}