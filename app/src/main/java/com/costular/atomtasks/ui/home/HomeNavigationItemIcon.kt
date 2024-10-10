package com.costular.atomtasks.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

@Composable
internal fun HomeNavigationItemIcon(
    destination: HomeNavigationDestination,
    selected: Boolean,
) {
    Crossfade(targetState = selected, label = "Icon") { selected ->
        Icon(
            imageVector = if (selected) destination.selectedIcon else destination.icon,
            contentDescription = stringResource(destination.contentDescriptionResId),
        )
    }
}
