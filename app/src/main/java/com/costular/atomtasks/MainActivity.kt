package com.costular.atomtasks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import com.costular.atomtasks.ui.features.NavGraphs
import com.costular.atomtasks.ui.features.agenda.AgendaScreen
import com.costular.atomtasks.ui.features.createtask.CreateTaskScreen
import com.costular.atomtasks.ui.features.destinations.AgendaScreenDestination
import com.costular.atomtasks.ui.features.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.ui.theme.AtomRemindersTheme
import com.costular.atomtasks.ui.util.DestinationsScaffold
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val systemUiController = rememberSystemUiController()
            val useDarkIcons = !isSystemInDarkTheme()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    Color.Transparent,
                    darkIcons = useDarkIcons
                )
            }

            AtomRemindersTheme {
                ProvideWindowInsets {
                    Navigation()
                }
            }
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
