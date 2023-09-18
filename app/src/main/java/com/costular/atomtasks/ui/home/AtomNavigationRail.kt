package com.costular.atomtasks.ui.home

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ramcosta.composedestinations.spec.NavGraphSpec

@Composable
fun AtomNavigationRail(
    selectedNavigation: NavGraphSpec,
    onNavigationSelected: (NavGraphSpec) -> Unit,
    modifier: Modifier = Modifier,
    header: @Composable ColumnScope.() -> Unit = {},
) {
    NavigationRail(
        modifier = modifier,
        header = header,
    ) {
        Spacer(Modifier.weight(1f))

        HomeNavigationDestination.values().forEach { destination ->
            val isSelected = selectedNavigation == destination.screen

            NavigationRailItem(
                selected = isSelected,
                onClick = { onNavigationSelected(destination.screen) },
                icon = {
                    HomeNavigationItemIcon(
                        destination = destination,
                        selected = isSelected,
                    )
                },
                label = {
                    Text(stringResource(destination.labelResId))
                },
            )
        }

        Spacer(Modifier.weight(1f))
    }
}
