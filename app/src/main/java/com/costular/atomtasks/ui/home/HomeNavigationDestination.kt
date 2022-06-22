package com.costular.atomtasks.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.ui.graphics.vector.ImageVector
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.features.destinations.AgendaScreenDestination
import com.costular.atomtasks.ui.features.destinations.DirectionDestination
import com.costular.atomtasks.ui.features.destinations.SettingsScreenDestination

enum class HomeNavigationDestination(
    val direction: DirectionDestination,
    val icon: ImageVector,
    @StringRes val label: Int,
) {
    Agenda(
        AgendaScreenDestination,
        Icons.Outlined.ViewAgenda,
        R.string.home_menu_agenda,
    ),
    Settings(
        SettingsScreenDestination,
        Icons.Outlined.Settings,
        R.string.home_menu_settings,
    )
}
