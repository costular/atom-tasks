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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomreminders.ui.theme.AtomRemindersTheme

@Composable
fun Chip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        border = BorderStroke(
            1.dp,
            MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled)
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
            Text("Today", style = MaterialTheme.typography.body1)
        }
    }
}