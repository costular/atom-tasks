package com.costular.atomtasks.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.ui.graphics.vector.ImageVector
import com.costular.atomtasks.core.ui.R
import com.ramcosta.composedestinations.generated.agenda.destinations.AgendaScreenDestination
import com.ramcosta.composedestinations.generated.settings.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class HomeNavigationDestination(
    val screen: DirectionDestinationSpec,
    @StringRes val contentDescriptionResId: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val labelResId: Int,
) {
    Agenda(
        screen = AgendaScreenDestination,
        contentDescriptionResId = R.string.home_menu_agenda,
        icon = Icons.Outlined.ViewAgenda,
        selectedIcon = Icons.Filled.ViewAgenda,
        labelResId = R.string.home_menu_agenda,
    ),
    Settings(
        screen = SettingsScreenDestination,
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        labelResId = R.string.home_menu_settings,
        contentDescriptionResId = R.string.home_menu_settings,
    ),
}
