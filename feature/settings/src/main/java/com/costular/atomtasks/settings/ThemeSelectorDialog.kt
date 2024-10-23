package com.costular.atomtasks.settings

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.costular.atomtasks.data.settings.Theme
import com.costular.designsystem.dialogs.AtomSheet
import com.costular.designsystem.theme.AppTheme
import com.costular.designsystem.theme.AtomTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator
import com.ramcosta.composedestinations.result.EmptyResultBackNavigator
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.costular.atomtasks.core.ui.R
import com.costular.atomtasks.settings.sections.parseTheme
import com.ramcosta.composedestinations.bottomsheet.spec.DestinationStyleBottomSheet

@Destination<SettingsGraph>(style = DestinationStyleBottomSheet::class)
@Composable
fun ThemeSelectorScreen(
    selectedTheme: String,
    resultNavigator: ResultBackNavigator<String>,
    navigator: DestinationsNavigator,
) {
    AtomSheet(
        title = stringResource(R.string.settings_theme_title),
        onNavigateUp = { navigator.navigateUp() },
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            ThemeItem(
                modifier = Modifier.weight(1f),
                theme = Theme.Light,
                isSelected = selectedTheme == Theme.LIGHT,
                onCheck = {
                    resultNavigator.navigateBack(Theme.LIGHT)
                },
            )

            ThemeItem(
                modifier = Modifier.weight(1f),
                theme = Theme.Dark,
                isSelected = selectedTheme == Theme.DARK,
                onCheck = {
                    resultNavigator.navigateBack(Theme.DARK)
                },
            )

            ThemeItem(
                modifier = Modifier.weight(1f),
                theme = Theme.System,
                isSelected = selectedTheme == Theme.SYSTEM,
                onCheck = {
                    resultNavigator.navigateBack(Theme.SYSTEM)
                },
            )
        }
    }
}

@Composable
private fun ThemeItem(
    theme: Theme,
    isSelected: Boolean,
    onCheck: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onCheck),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(80.dp),
            painter = painterResource(parseThemeIconDrawable(theme)),
            contentDescription = null,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        Text(
            text = parseTheme(theme),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Spacer(Modifier.height(AppTheme.dimens.spacingLarge))

        RadioButton(
            selected = isSelected,
            onClick = onCheck,
        )
    }
}

@DrawableRes
private fun parseThemeIconDrawable(theme: Theme): Int = when (theme) {
    is Theme.Light -> R.drawable.ic_theme_light
    is Theme.Dark -> R.drawable.ic_theme_dark
    is Theme.System -> R.drawable.ic_theme_system
}

@Preview(showBackground = true)
@Composable
fun ThemeSelectorPreview() {
    AtomTheme {
        ThemeSelectorScreen(
            selectedTheme = Theme.LIGHT,
            navigator = EmptyDestinationsNavigator,
            resultNavigator = EmptyResultBackNavigator(),
        )
    }
}
