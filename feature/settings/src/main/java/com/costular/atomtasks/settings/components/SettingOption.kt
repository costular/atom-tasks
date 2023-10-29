package com.costular.atomtasks.settings.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.designsystem.theme.AtomTheme

@Composable
fun SettingOption(
    title: String,
    option: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    SettingItem(
        start = {
            Icon(
                painter = rememberVectorPainter(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.Top),
            )
        },
        title = {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        },
        end = {
            Icon(
                Icons.Default.ChevronRight,
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
        SettingOption(
            title = "Theme",
            option = "Light",
            icon = Icons.Outlined.Palette,
            onClick = {},
        )
    }
}
