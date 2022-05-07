package com.costular.atomtasks.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomtasks.ui.theme.AppTheme

@Composable
fun SettingItem(
    title: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    start: @Composable (() -> Unit)? = null,
    end: @Composable (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colors.surface)
            .clickable { onClick() }
            .padding(AppTheme.dimens.contentMargin),
    ) {
        if (start != null) {
            start()
            Spacer(Modifier.width(AppTheme.dimens.spacingXLarge))
        }

        Box(Modifier.weight(1f)) {
            title()
        }

        if (end != null) {
            Spacer(Modifier.width(AppTheme.dimens.contentMargin))
            end()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JustTitlePreview() {
    AtomRemindersTheme {
        SettingItem(
            title = {
                Text("This is a test with only a title, nothing else")
            },
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleAndStartPreview() {
    AtomRemindersTheme {
        SettingItem(
            title = {
                Text("This is a test with start also")
            },
            start = {
                Icon(Icons.Default.Search, null)
            },
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TitleAndStartAndEndPreview() {
    AtomRemindersTheme {
        SettingItem(
            title = {
                Text("This is a test with start and end wadwadawdaw daw daw daw awdaw dawd sdadad")
            },
            start = {
                Icon(Icons.Default.Search, null)
            },
            end = {
                Switch(
                    checked = true,
                    onCheckedChange = { },
                )
            },
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
