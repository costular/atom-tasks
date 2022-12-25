package com.costular.atomtasks.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.coreui.DestinationsBottomSheet
import com.costular.atomtasks.coreui.DestinationsScaffold
import com.costular.atomtasks.coreui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.data.settings.Theme
import com.costular.atomtasks.ui.AppNavigation
import com.costular.atomtasks.ui.NavGraphs
import com.costular.atomtasks.ui.currentScreenAsState
import com.costular.designsystem.theme.AtomTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun App(
    windowSizeClass: WindowSizeClass,
) {
    val viewModel: AppViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(AppState.Empty)
    val isSystemDarkMode = isSystemInDarkTheme()

    val systemUiController = rememberSystemUiController()
    val isDarkTheme = remember(state.theme) {
        when (state.theme) {
            is Theme.Light -> false
            is Theme.Dark -> true
            is Theme.System -> isSystemDarkMode
        }
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            Color.Transparent,
            darkIcons = !isDarkTheme,
        )
    }

    AtomTheme(darkTheme = isDarkTheme) {
        Home(
            atomAppState = rememberAtomAppState(
                navController = rememberAnimatedNavController(),
                windowSizeClass = windowSizeClass,
            ),
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Home(
    atomAppState: AtomAppState,
) {
    val currentSelectedItem by atomAppState.navController.currentScreenAsState()

    when (atomAppState.atomNavigationType) {
        AtomNavigationType.BOTTOM_NAVIGATION -> {
            DestinationsScaffold(
                bottomBar = {
                    AtomBottomNavigation(
                        selectedNavigation = currentSelectedItem,
                        onNavigationSelected = { selected ->
                            atomAppState.navigateToTopLevelDestination(selected)
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                floatingActionButton = {
                    if (currentSelectedItem == NavGraphs.agenda) {
                        FloatingActionButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Outlined.Add, contentDescription = null)
                        }
                    }
                },
                navController = atomAppState.navController,
            ) { paddingValues ->
                AppNavigation(
                    modifier = Modifier.padding(paddingValues),
                    appState = atomAppState,
                )
            }
        }
        AtomNavigationType.RAIL_NAVIGATION -> {
            DestinationsBottomSheet(
                navController = atomAppState.navController,
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    AtomNavigationRail(
                        selectedNavigation = currentSelectedItem,
                        onNavigationSelected = { selected ->
                            atomAppState.navigateToTopLevelDestination(selected)
                        },
                        header = {
                            if (currentSelectedItem == NavGraphs.agenda) {
                                FloatingActionButton(onClick = { /*TODO*/ }) {
                                    Icon(Icons.Outlined.Add, contentDescription = null)
                                }
                            }
                        },
                        modifier = Modifier.safeDrawingPadding(),
                    )

                    Divider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                            .safeDrawingPadding(),
                    )

                    AppNavigation(
                        appState = atomAppState,
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}
