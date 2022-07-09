package com.costular.atomtasks.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.ramcosta.composedestinations.spec.NavGraphSpec

@Composable
fun AtomBottomNavigation(
    selectedNavigation: NavGraphSpec,
    onNavigationSelected: (NavGraphSpec) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomNavigation(
        modifier = modifier.navigationBarsPadding(),
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp,
    ) {
        HomeNavigationDestination.values().forEach { destination ->
            BottomNavigationItem(
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
private fun HomeNavigationItemIcon(
    destination: HomeNavigationDestination,
    selected: Boolean,
) {
    Crossfade(targetState = selected) {
        Icon(
            imageVector = destination.icon,
            contentDescription = stringResource(destination.contentDescriptionResId),
        )
    }
}
