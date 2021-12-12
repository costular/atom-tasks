package com.costular.atomreminders.ui.components

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomreminders.R
import com.costular.atomreminders.ui.theme.AtomRemindersTheme

@Composable
fun Chip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.background,
    isError: Boolean = false,
    content: @Composable RowScope.() -> Unit,
) {
    val borderColor =
        if (isError) MaterialTheme.colors.error else contentColorFor(backgroundColor).copy(alpha = ContentAlpha.disabled)

    Surface(
        color = backgroundColor,
        border = BorderStroke(
            1.dp,
            borderColor,
        ),
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .clip(MaterialTheme.shapes.small)
            .clickable(onClick = onClick),
    ) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                content()
            }
        }
    }
}

@Preview
@Composable
private fun ChipPreview() {
    AtomRemindersTheme {
        Chip(onClick = {}) {
            Icon(
                imageVector = Icons.Default.CalendarToday,
                contentDescription = null
            )
            Spacer(Modifier.width(16.dp))
            Text(stringResource(R.string.today), style = MaterialTheme.typography.body1)
        }
    }
}