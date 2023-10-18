package com.costular.atomtasks.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.theme.AtomTheme

@Composable
fun SettingsAboutSection(
    onOpenDonation: () -> Unit,
) {
    SettingSection(
        title = stringResource(R.string.settings_about_title),
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingLink(
            title = {
                Column {
                    Text(
                        text = stringResource(R.string.settings_about_donate_title),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = stringResource(
                            R.string.settings_about_donate_description,
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            },
            icon = Icons.Outlined.VolunteerActivism,
            onClick = onOpenDonation,
        )
    }
}

@Preview
@Composable
fun SettingsAboutSectionPreview() {
    AtomTheme {
        SettingsAboutSection(
            onOpenDonation = {},
        )
    }
}
