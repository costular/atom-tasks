package com.costular.commonui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.costular.atomtasks.domain.model.Reminder
import com.costular.commonui.theme.AppTheme
import com.costular.commonui.theme.AtomRemindersTheme
import com.costular.core.util.DateTimeFormatters
import java.time.LocalDate
import java.time.LocalTime

const val ReminderIconId = "reminder"

@Composable
fun TaskCard(
    title: String,
    isFinished: Boolean,
    reminder: Reminder?,
    onMark: () -> Unit,
    onOpen: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val mediumColor = MaterialTheme.colors.onBackground.copy(alpha = ContentAlpha.medium)

    Surface(
        modifier
            .fillMaxWidth()
            .clickable { onOpen() }
            .padding(vertical = AppTheme.dimens.spacingSmall),
        color = MaterialTheme.colors.background,
    ) {
        val reminderInlineContent = reminderInline(mediumColor)

        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Markable(
                isMarked = isFinished,
                borderColor = mediumColor,
                onClick = { onMark() },
                contentColor = MaterialTheme.colors.primary,
                onContentColor = MaterialTheme.colors.onPrimary,
            )

            Spacer(modifier = Modifier.width(AppTheme.dimens.spacingLarge))

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6.copy(
                        textDecoration = if (isFinished) TextDecoration.LineThrough else null,
                    ),
                )
                AnimatedVisibility(reminder != null && !isFinished) {
                    Row {
                        val alarmText = buildAnnotatedString {
                            appendInlineContent(ReminderIconId, "[alarm]")
                            append(" ")
                            append(reminderAsText(requireNotNull(reminder)))
                        }
                        Text(
                            text = alarmText,
                            style = MaterialTheme.typography.body2,
                            color = mediumColor,
                            inlineContent = reminderInlineContent,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun reminderInline(mediumColor: Color) = mapOf(
    Pair(
        ReminderIconId,
        InlineTextContent(
            Placeholder(
                width = 16.sp,
                height = 16.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Center,
            ),
        ) {
            Icon(
                imageVector = Icons.Outlined.Alarm,
                contentDescription = null,
                tint = mediumColor,
            )
        },
    ),
)

fun reminderAsText(reminder: Reminder): String =
    DateTimeFormatters.timeFormatter.format(reminder.time)

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    AtomRemindersTheme {
        TaskCard(
            title = "Run every morning!",
            isFinished = true,
            onMark = {},
            onOpen = {},
            reminder = Reminder(1L, LocalTime.parse("10:00"), true, LocalDate.now()),
        )
    }
}
