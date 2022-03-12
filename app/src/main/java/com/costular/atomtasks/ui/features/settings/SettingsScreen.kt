package com.costular.atomtasks.ui.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.costular.atomtasks.ui.settings.SettingOption
import com.costular.atomtasks.ui.settings.SettingSection
import com.costular.atomtasks.ui.settings.SettingSwitch
import com.costular.atomtasks.ui.theme.AppTheme
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.google.android.gms.tasks.Tasks

@Composable
fun SettingsScreen() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .verticalScroll(scrollState)
            .padding(top = AppTheme.dimens.contentMargin)
            .padding(bottom = AppTheme.dimens.contentMargin),
    ) {
        GeneralSection()
        SectionSpacer()
        TasksSection()
        SectionSpacer()
        DateTimeSection()
    }
}

@Composable
private fun SectionSpacer() {
    Spacer(Modifier.height(AppTheme.dimens.spacingLarge))
}

@Composable
private fun DateTimeSection() {
    SettingSection(
        title = "Fecha y/o hora",
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingOption(
            title = "Primer día de la semana",
            option = "Lunes",
            icon = Icons.Outlined.DateRange,
            onClick = {
                // TODO:
            },
        )

        SettingOption(
            title = "Formato fecha",
            option = "dd/mm/yyyy",
            icon = Icons.Outlined.CalendarToday,
            onClick = {
                // TODO:
            },
        )

        SettingOption(
            title = "Formato hora",
            option = "24h (21:00)",
            icon = Icons.Outlined.Schedule,
            onClick = {
                // TODO:
            },
        )
    }
}

@Composable
private fun TasksSection() {
    SettingSection(
        title = "Tasks",
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingSwitch(
            title = {
                Text(
                    text = "Pasar tareas no completadas al día siguiente automáticamente",
                    style = MaterialTheme.typography.body1,
                )
            },
            isSelected = true,
            onSelect = {
                // TODO:
            },
        )
    }
}

@Composable
private fun GeneralSection() {
    SettingSection(
        "General",
        modifier = Modifier.fillMaxWidth(),
    ) {
        SettingOption(
            title = "Theme",
            option = "Light",
            icon = Icons.Outlined.Palette,
            onClick = {
                // TODO:
            },
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun SettingsScreenPreview() {
    AtomRemindersTheme {
        SettingsScreen()
    }
}
