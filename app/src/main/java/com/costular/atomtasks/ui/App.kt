package com.costular.atomtasks.ui

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.costular.atomtasks.domain.model.Theme
import com.costular.atomtasks.ui.features.NavGraphs
import com.costular.atomtasks.ui.features.agenda.AgendaScreen
import com.costular.atomtasks.ui.features.createtask.CreateTaskScreen
import com.costular.atomtasks.ui.features.destinations.AgendaScreenDestination
import com.costular.atomtasks.ui.features.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DestinationsScaffold
import com.costular.atomtasks.ui.util.rememberFlowWithLifecycle
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable

@Composable
fun App() {
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
            darkIcons = !isDarkTheme
        )
    }

    AtomRemindersTheme(darkTheme = isDarkTheme) {
        ProvideWindowInsets {
            Navigation()
        }
    }
}

@Composable
private fun Navigation() {
    val scaffoldState = rememberScaffoldState()
    val navController = rememberAnimatedNavController()

    DestinationsScaffold(
        scaffoldState = scaffoldState,
        navController = navController,
    ) { paddingValues ->
        NavigationContent(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
        )
    }
}

@Composable
private fun NavigationContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navHostEngine = rememberAnimatedNavHostEngine()

    DestinationsNavHost(
        engine = navHostEngine,
        navController = navController,
        navGraph = NavGraphs.root,
        modifier = modifier,
    ) {
        composable(AgendaScreenDestination) {
            AgendaScreen(destinationsNavigator)
        }
        composable(CreateTaskScreenDestination) {
            CreateTaskScreen(
                text = navArgs.text,
                date = navArgs.date,
                navigator = destinationsNavigator
            )
        }
    }
}
