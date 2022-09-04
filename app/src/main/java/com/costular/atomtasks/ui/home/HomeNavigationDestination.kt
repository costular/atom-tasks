package com.costular.atomtasks.ui.home

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ViewAgenda
import androidx.compose.ui.graphics.vector.ImageVector
import com.costular.atomtasks.R
import com.costular.atomtasks.ui.NavGraphs
import com.ramcosta.composedestinations.spec.NavGraphSpec

enum class HomeNavigationDestination(
    val screen: NavGraphSpec,
    @StringRes val contentDescriptionResId: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    @StringRes val labelResId: Int,
) {
    Agenda(
        screen = NavGraphs.agenda,
        contentDescriptionResId = R.string.home_menu_agenda,
        icon = Icons.Outlined.ViewAgenda,
        selectedIcon = Icons.Filled.ViewAgenda,
        labelResId = R.string.home_menu_agenda,
    ),
    Settings(
        screen = NavGraphs.settings,
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings,
        labelResId = R.string.home_menu_settings,
        contentDescriptionResId = R.string.home_menu_settings,
    )
}

