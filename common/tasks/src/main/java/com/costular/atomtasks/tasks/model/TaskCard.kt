package com.costular.atomtasks.tasks.model


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Alarm
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.costular.atomtasks.coreui.utils.ofLocalizedTime
import com.costular.designsystem.components.Markable
import com.costular.designsystem.decorator.strikeThrough
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import java.time.LocalDate
import java.time.LocalTime

const val ReminderIconId = "reminder"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskCard(
    title: String,
    isFinished: Boolean,
    reminder: Reminder?,
    onMark: () -> Unit,
    onOpen: () -> Unit,
    isBeingDragged: Boolean,
    modifier: Modifier = Modifier,
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    mutableInteractionSource.reorderableDragInteractions(isDragging = isBeingDragged)

    HandleHapticForInteractions(mutableInteractionSource)

    val mediumColor = MaterialTheme.colorScheme.onSurfaceVariant
    val shouldShowReminder = remember(isFinished, reminder) { reminder != null && !isFinished }

    ElevatedCard(
        onClick = onOpen,
        modifier = modifier,
        interactionSource = mutableInteractionSource,
        colors = CardDefaults.cardColors(),
    ) {
        val reminderInlineContent = reminderInline(mediumColor)

        Row(
            modifier = Modifier.padding(AppTheme.dimens.spacingLarge),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Markable(
                isMarked = isFinished,
                borderColor = mediumColor,
                onClick = { onMark() },
                contentColor = MaterialTheme.colorScheme.primary,
                onContentColor = MaterialTheme.colorScheme.onPrimary,
            )

            Spacer(modifier = Modifier.width(AppTheme.dimens.spacingLarge))

            Column {
                TaskTitle(isFinished = isFinished, title = title)

                if (shouldShowReminder) {
                    Spacer(Modifier.height(AppTheme.dimens.spacingSmall))
                }

                AnimatedVisibility(shouldShowReminder) {
                    if (reminder != null) {
                        Row {
                            val alarmText = buildAnnotatedString {
                                appendInlineContent(ReminderIconId, "[alarm]")
                                append(" ")
                                append(reminder.time.ofLocalizedTime())
                            }
                            Text(
                                text = alarmText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = mediumColor,
                                inlineContent = reminderInlineContent,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TaskTitle(isFinished: Boolean, title: String) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    val progress by animateFloatAsState(
        targetValue = if (isFinished) 1f else 0f,
        animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing),
        label = "Task strike through"
    )

    Text(
        text = title,
        onTextLayout = {
            textLayoutResult = it
        },
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.strikeThrough(
            color = LocalContentColor.current,
            border = 2.dp,
            progress = { progress },
            textLayoutResult = textLayoutResult,
            enabled = isFinished,
        )
    )
}

@Composable
private fun HandleHapticForInteractions(interactionSource: MutableInteractionSource) {
    val haptic = LocalHapticFeedback.current
    val isDragging by interactionSource.collectIsDraggedAsState()

    LaunchedEffect(isDragging) {
        if (isDragging) {
            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
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

@Composable
private fun MutableInteractionSource.reorderableDragInteractions(isDragging: Boolean) {
    val dragState = remember {
        object {
            var start: DragInteraction.Start? = null
        }
    }
    LaunchedEffect(isDragging) {
        when (val start = dragState.start) {
            null -> if (isDragging) dragState.start = DragInteraction.Start().also { emit(it) }
            else -> if (!isDragging) {
                dragState.start = null
                emit(DragInteraction.Stop(start))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardPreview() {
    AtomTheme {
        TaskCard(
            title = "Run every morning!",
            isFinished = true,
            onMark = {},
            onOpen = {},
            reminder = Reminder(
                1L,
                LocalTime.parse("10:00"),
                true,
                LocalDate.now(),
            ),
            isBeingDragged = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardUnfinishedPreview() {
    AtomTheme {
        TaskCard(
            title = "Run every morning!",
            isFinished = false,
            onMark = {},
            onOpen = {},
            reminder = Reminder(
                1L,
                LocalTime.parse("10:00"),
                true,
                LocalDate.now(),
            ),
            isBeingDragged = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskCardLongNamePreview() {
    AtomTheme {
        TaskCard(
            title = "A task with a really long name in order to see the strike thorugh",
            isFinished = true,
            onMark = {},
            onOpen = {},
            reminder = Reminder(
                1L,
                LocalTime.parse("10:00"),
                true,
                LocalDate.now(),
            ),
            isBeingDragged = false,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
