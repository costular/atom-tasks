package com.costular.atomtasks.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.spec.NavGraphSpec

@Composable
fun AtomBottomNavigation(
    selectedNavigation: NavGraphSpec,
    onNavigationSelected: (NavGraphSpec) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        HomeNavigationDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = selectedNavigation == destination.screen,
                onClick = {
                    onNavigationSelected(destination.screen)
                },
                icon = {
                    HomeNavigationItemIcon(
                        destination = destination,
                        selected = selectedNavigation == destination.screen,
                    )
                },
                label = { Text(stringResource(destination.labelResId)) },
            )
        }
    }
}

@Composable
internal fun HomeNavigationItemIcon(
    destination: HomeNavigationDestination,
    selected: Boolean,
) {
    Crossfade(targetState = selected) {
        Icon(
            imageVector = if (selected) destination.selectedIcon else destination.icon,
            contentDescription = stringResource(destination.contentDescriptionResId),
        )
    }
}
