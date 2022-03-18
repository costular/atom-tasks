package com.costular.atomtasks.ui.features.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.R
import com.costular.atomtasks.domain.model.Theme
import com.costular.atomtasks.ui.settings.SettingOption
import com.costular.atomtasks.ui.settings.SettingSection

@Composable
internal fun GeneralSection(
    theme: Theme,
    onSelectTheme: () -> Unit,
) {
    SettingSection(
        stringResource(R.string.settings_general_title),
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingOption(
            title = stringResource(R.string.settings_theme_title),
            option = parseTheme(theme),
            icon = Icons.Outlined.Palette,
            onClick = onSelectTheme,
        )
    }
}

@Composable
internal fun parseTheme(theme: Theme): String = when (theme) {
    is Theme.Light -> stringResource(R.string.settings_theme_light)
    is Theme.Dark -> stringResource(R.string.settings_theme_dark)
    is Theme.System -> stringResource(R.string.settings_theme_system)
}
