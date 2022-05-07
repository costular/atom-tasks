package com.costular.atomtasks.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.ui.features.settings.SettingItem
import com.costular.atomtasks.ui.theme.AtomRemindersTheme

@Composable
fun SettingOption(
    title: String,
    option: String,
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
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                )

                Spacer(Modifier.height(2.dp))

                Text(
                    text = option,
                    style = MaterialTheme.typography.body2,
                )
            }
        },
        end = {
            Image(
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
    AtomRemindersTheme {
        SettingOption(
            title = "Theme",
            option = "Light",
            icon = Icons.Outlined.Palette,
            onClick = {},
        )
    }
}
