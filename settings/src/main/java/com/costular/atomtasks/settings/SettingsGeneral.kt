package com.costular.atomtasks.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.costular.atomtasks.domain.model.Theme

@Composable
internal fun GeneralSection(
    theme: Theme,
    onSelectTheme: (String) -> Unit,
) {
    var showThemeDialog by rememberSaveable { mutableStateOf(false) }

    if (showThemeDialog) {
        ThemeSelectorDialog(
            selectedTheme = theme.asString(),
            onSelectTheme = onSelectTheme,
            onNavigateUp = {
                showThemeDialog = false
            },
        )
    }

    SettingSection(
        stringResource(R.string.settings_general_title),
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingOption(
            title = stringResource(R.string.settings_theme_title),
            option = parseTheme(theme),
            icon = Icons.Outlined.Palette,
            onClick = { showThemeDialog = true },
        )
    }
}

@Composable
internal fun parseTheme(theme: Theme): String = when (theme) {
    is Theme.Light -> stringResource(R.string.settings_theme_light)
    is Theme.Dark -> stringResource(R.string.settings_theme_dark)
    is Theme.System -> stringResource(R.string.settings_theme_system)
}
