package com.costular.atomtasks.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.settings.components.SettingItem
import com.costular.designsystem.theme.AtomTheme

@Composable
fun SettingLink(
    title: @Composable () -> Unit,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    SettingItem(
        start = {
            Image(
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            )
        },
        title = title,
        end = {
            Image(
                Icons.AutoMirrored.Filled.OpenInNew,
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
    AtomTheme {
        SettingLink(
            title = {
                Text(
                    text = "GitHub repository",
                    style = MaterialTheme.typography.titleSmall,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )
            },
            icon = Icons.Outlined.Code,
            onClick = {},
        )
    }
}
