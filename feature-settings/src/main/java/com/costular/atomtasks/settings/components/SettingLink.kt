package com.costular.atomtasks.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.outlined.Code
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.commonui.theme.AtomRemindersTheme

@Composable
fun SettingLink(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    SettingItem(
        start = {
            Image(
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            )
        },
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        },
        end = {
            Image(
                Icons.Default.OpenInNew,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        },
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingOptionPrev() {
    AtomRemindersTheme {
        SettingLink(
            title = "GitHub repository",
            icon = Icons.Outlined.Code,
            onClick = {},
        )
    }
}
