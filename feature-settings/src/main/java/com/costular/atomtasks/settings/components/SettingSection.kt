package com.costular.atomtasks.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.commonui.theme.AppTheme

@Composable
fun SettingSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.contentMargin)
                .padding(vertical = AppTheme.dimens.spacingMedium),
            style = MaterialTheme.typography.titleMedium,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimens.contentMargin)
                .clip(RoundedCornerShape(16.dp)),
            content = content,
        )
    }

    Spacer(Modifier.height(AppTheme.dimens.spacingMedium))
}

@Preview(showBackground = true)
@Composable
private fun SettingSectionPreview() {
    Column {
        SettingSection(title = "Whatever") {
            Text(
                "This is a section",
                modifier = Modifier.padding(AppTheme.dimens.contentMargin),
            )
        }
    }
}
