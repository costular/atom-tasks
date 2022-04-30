package com.costular.atomtasks.ui.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.costular.atomtasks.ui.features.destinations.Destination
import com.costular.atomtasks.ui.features.navDestination
import com.google.accompanist.insets.navigationBarsPadding
import com.ramcosta.composedestinations.navigation.navigateTo

@Composable
fun AtomBottomNavigation(
    navController: NavController,
) {
    val currentDestination: Destination? = navController.currentBackStackEntryAsState()
        .value?.navDestination

    BottomNavigation(
        modifier = Modifier.navigationBarsPadding(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    ) {
        HomeNavigationDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                onClick = {
                    navController.navigateTo(destination.direction) {
                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(destination.icon, contentDescription = stringResource(destination.label)) },
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}
