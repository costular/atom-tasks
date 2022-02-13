package com.costular.atomtasks.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun AtomTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    elevation: Dp = 0.dp,
) {
    TopAppBar(
        title = title,
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        backgroundColor = MaterialTheme.colors.background,
        elevation = elevation,
    )
}
