package com.costular.atomhabits.ui.features.habits.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import com.costular.atomhabits.domain.model.Reminder
import com.vanpra.composematerialdialogs.MaterialDialog
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.costular.atomhabits.R
import com.costular.atomhabits.ui.components.ScreenHeader
import com.costular.atomhabits.ui.util.DateTimeFormatters
import com.vanpra.composematerialdialogs.buttons
import com.vanpra.composematerialdialogs.datetime.timepicker.timepicker
import java.time.LocalTime

@Composable
fun CreateHabitReminder(
    state: CreateHabitState,
    onChangeReminderEnabled: (Boolean) -> Unit,
    onChangeReminderTime: (LocalTime) -> Unit
) {
    val dialog = remember { MaterialDialog() }

    dialog.build {
        timepicker(initialTime = state.reminderTime) { time ->
            onChangeReminderTime(time)
        }
        buttons {
            positiveButton("Ok")
            negativeButton("Cancel")
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
                                onClick = { dialog.show() },
                                interactionSource = interactionSource,
                                indication = null
                            )
                    )
                }

            }
        }
    }

}