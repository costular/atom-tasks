package com.costular.atomreminders.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.costular.atomreminders.domain.model.*
import com.costular.atomreminders.ui.util.DateTimeFormatters
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TaskCard(
    title: String,
    isFinished: Boolean,
    reminder: Reminder?,
    onMark: () -> Unit,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val mediumColor = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)

    Surface(
        modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .padding(vertical = 4.dp),
        color = MaterialTheme.colors.background
    ) {
        val reminderIconId = "alarm"
        val reminderInlineContent = mapOf(
            Pair(
                reminderIconId,
                InlineTextContent(
                    Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Alarm,
                        contentDescription = null,
                        tint = mediumColor,
                    )
                }
            )
        )


        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Markable(
                isMarked = isFinished,
                borderColor = mediumColor,
                onClick = { onMark() },
                contentColor = MaterialTheme.colors.primary,
                onContentColor = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6
                )
                Row {
                    if (reminder != null) {
                        val alarmText = buildAnnotatedString {
                            appendInlineContent(reminderIconId, "[alarm]")
                            append(" ")
                            append(reminderAsText(reminder))
                        }
                        Text(
                            text = alarmText,
                            style = MaterialTheme.typography.body1,
                            color = mediumColor,
                            inlineContent = reminderInlineContent
                        )
                    }
                }
            }
        }
    }
}

fun reminderAsText(reminder: Reminder): String =
    DateTimeFormatters.timeFormatter.format(reminder.time)

@Preview(showBackground = true)
@Composable
private fun HabitCardPreview() {
    TaskCard(
        title = "Run every morning!",
        isFinished = true,
        onMark = {},
        onOpen = {},
        reminder = Reminder(1L, LocalTime.parse("10:00"), true, LocalDate.now())
    )
}