package com.costular.atomtasks.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme

@Composable
fun ActionItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(AppTheme.dimens.contentMargin),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, contentDescription = null)

        Spacer(Modifier.width(AppTheme.dimens.spacingLarge))

        Text(
            text = text,
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ActionMenuItemPreview() {
    AtomRemindersTheme {
        ActionItem(
            Icons.Outlined.Delete,
            "Remove",
            onClick = {},
        )
    }
}
