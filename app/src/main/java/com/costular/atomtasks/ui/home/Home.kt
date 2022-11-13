package com.costular.atomtasks.ui.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.costular.atomtasks.coreui.DestinationsScaffold
import com.costular.atomtasks.coreui.utils.DevicesPreview
import com.costular.atomtasks.coreui.utils.generateWindowSizeClass
import com.costular.atomtasks.coreui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.data.settings.Theme
import com.costular.atomtasks.ui.AppNavigation
import com.costular.atomtasks.ui.currentScreenAsState
import com.costular.designsystem.theme.AtomRemindersTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.navigation.navigate

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

    AtomRemindersTheme(darkTheme = isDarkTheme) {
        Home(windowSizeClass = windowSizeClass)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun Home(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberAnimatedNavController(),
) {
    val currentSelectedItem by navController.currentScreenAsState()

    DestinationsScaffold(
        bottomBar = {
            AtomBottomNavigation(
                selectedNavigation = currentSelectedItem,
                onNavigationSelected = { selected ->
                    navController.navigate(selected) {
                        launchSingleTop = true
                        restoreState = true

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        },
        navController = navController,
    ) { paddingValues ->
        AppNavigation(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            windowSizeClass = windowSizeClass,
        )
    }
}

@DevicesPreview
@Composable
private fun HomePreview() {
    val windowSizeClass = generateWindowSizeClass()

    AtomRemindersTheme {
        Home(
            windowSizeClass = windowSizeClass,
        )
    }
}
