package com.costular.atomtasks.ui.home

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.costular.atomtasks.agenda.destinations.AgendaScreenDestination
import com.costular.atomtasks.settings.destinations.SettingsScreenDestination
import com.costular.atomtasks.settings.destinations.ThemeSelectorScreenDestination
import com.ramcosta.composedestinations.spec.NavGraphSpec

@Composable
fun rememberAtomAppState(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
): AtomAppState {
    return remember(navController, windowSizeClass) {
        AtomAppState(navController, windowSizeClass)
    }
}

@Stable
class AtomAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass,
) {
    private val currentDestination: NavBackStackEntry?
        @Composable get() = navController.currentBackStackEntryAsState().value

    val shouldShowNavigation: Boolean
        @Composable get() = currentDestination?.destination?.route in listOf(
            AgendaScreenDestination,
            SettingsScreenDestination,
            ThemeSelectorScreenDestination,
        ).map { it.route }

    val atomNavigationType: AtomNavigationType
        get() = when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact ->
                AtomNavigationType.BOTTOM_NAVIGATION

            WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded ->
                AtomNavigationType.RAIL_NAVIGATION

            else -> AtomNavigationType.BOTTOM_NAVIGATION
        }

    fun navigateToTopLevelDestination(selected: NavGraphSpec) {
        navController.navigate(selected.route) {
            launchSingleTop = true
            restoreState = true

            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }
}
