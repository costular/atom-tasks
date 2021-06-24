package com.costular.atomhabits.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.costular.atomhabits.R
import com.costular.atomhabits.domain.model.*

@Composable
fun HabitCard(
    title: String,
    isFinished: Boolean,
    repetition: Repetition,
    onMark: (Boolean) -> Unit,
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
        val repetitionIconId = "repetition"
        val repetitionText = buildAnnotatedString {
            appendInlineContent(repetitionIconId, "[repetition]")
            append(" ")
            append(repetitionAsText(repetition))
        }
        val inlineContent = mapOf(
            Pair(
                repetitionIconId,
                InlineTextContent(
                    Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.CalendarToday,
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
                onClick = { onMark(!isFinished) },
                contentColor = MaterialTheme.colors.primary,
                onContentColor = MaterialTheme.colors.onPrimary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h6
                )
                Text(
                    text = repetitionText,
                    style = MaterialTheme.typography.body1,
                    color = mediumColor,
                    inlineContent = inlineContent
                )
            }
        }
    }
}

@Composable
fun repetitionAsText(repetition: Repetition): String = when (repetition) {
    is Daily -> stringResource(R.string.repetition_everyday)
    is Weekly -> stringResource(R.string.repetition_weekly)
}

@Preview(showBackground = true)
@Composable
private fun HabitCardPreview() {
    HabitCard(
        title = "Run every morning!",
        isFinished = true,
        repetition = Daily,
        onMark = {},
        onOpen = {}
    )
}