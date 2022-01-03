package com.costular.atomtasks.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme

@Composable
fun Chip(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentPadding: PaddingValues = PaddingValues(AppTheme.dimens.spacingMedium),
    content: @Composable RowScope.() -> Unit,
) {
    Surface(
        color = backgroundColor,
        shape = shape,
        border = border,
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick),
    ) {
        Row(
            Modifier.padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                contentDescription = null,
                modifier = Modifier.size(AppTheme.ChipIconSize)
            )
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.today), style = MaterialTheme.typography.body1)
        }
    }
}