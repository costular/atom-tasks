package com.costular.atomtasks.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
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
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.core.ui.DestinationsBottomSheet
import com.costular.atomtasks.core.ui.DestinationsScaffold
import com.costular.atomtasks.data.settings.Theme
import com.costular.atomtasks.ui.AppNavigation
import com.costular.atomtasks.ui.NavGraphs
import com.costular.atomtasks.ui.currentScreenAsState
import com.costular.designsystem.theme.AtomTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.costular.atomtasks.core.ui.R.string as S

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun App(
    windowSizeClass: WindowSizeClass,
) {
    val viewModel: AppViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
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
        val engine = rememberAnimatedNavHostEngine()
        val navController = engine.rememberNavController()

        Home(
            atomAppState = rememberAtomAppState(
                navController = navController,
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
    val (fabOnClick, setFabOnClick) = remember { mutableStateOf<(() -> Unit)?>(null) }

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
                    AddTaskFloatingActionButton(
                        currentSelectedItem = currentSelectedItem,
                        fabOnclick = fabOnClick,
                        shouldBeExpanded = true,
                    )
                },
                navController = atomAppState.navController,
            ) { paddingValues ->
                AppNavigation(
                    modifier = Modifier.padding(paddingValues),
                    appState = atomAppState,
                    fabClick = setFabOnClick,
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
                            AddTaskFloatingActionButton(
                                currentSelectedItem = currentSelectedItem,
                                fabOnclick = fabOnClick,
                                shouldBeExpanded = false,
                            )
                        },
                        modifier = Modifier.safeDrawingPadding(),
                    )

                    Spacer(Modifier.width(24.dp))

                    AppNavigation(
                        appState = atomAppState,
                        modifier = Modifier.weight(1f),
                        fabClick = setFabOnClick,
                    )
                }
            }
        }
    }
}

@Composable
private fun AddTaskFloatingActionButton(
    currentSelectedItem: NavGraphSpec,
    shouldBeExpanded: Boolean,
    fabOnclick: (() -> Unit)?,
) {
    AnimatedVisibility(
        visible = currentSelectedItem == NavGraphs.agenda,
        enter = scaleIn(
            animationSpec = tween(
                easing = FastOutSlowInEasing,
            ),
        ),
        exit = scaleOut(
            animationSpec = tween(
                easing = FastOutSlowInEasing,
            ),
        ),
    ) {
        if (shouldBeExpanded) {
            ExtendedFloatingActionButton(
                onClick = {
                    fabOnclick?.invoke()
                },
                text = {
                    Text(stringResource(S.agenda_create_new_task))
                },
                icon = {
                    Icon(Icons.Outlined.Add, contentDescription = null)
                }
            )
        } else {
            FloatingActionButton(onClick = {
                fabOnclick?.invoke()
            }) {
                Icon(Icons.Outlined.Add, contentDescription = null)
            }
        }
    }
}
