package com.costular.atomtasks.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.costular.atomtasks.agenda.destinations.AgendaScreenDestination
import com.costular.atomtasks.createtask.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.settings.destinations.SettingsScreenDestination
import com.costular.atomtasks.ui.home.AppNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.DestinationScope
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object NavGraphs {

    val agenda = object : NavGraphSpec {
        override val route: String = "agenda"

        override val startRoute: Route = AgendaScreenDestination

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            AgendaScreenDestination,
        ).associateBy { it.route }
    }

    val createTask = object : NavGraphSpec {
        override val route: String = "createtask"

        override val startRoute: Route = CreateTaskScreenDestination

        override val destinationsByRoute: Map<String, DestinationSpec<*>> =
            listOf<DestinationSpec<*>>(
                CreateTaskScreenDestination,
            ).associateBy { it.route }
    }

    val settings = object : NavGraphSpec {
        override val route: String = "settings"

        override val startRoute: Route = SettingsScreenDestination

        override val destinationsByRoute: Map<String, DestinationSpec<*>> =
            listOf<DestinationSpec<*>>(
                SettingsScreenDestination,
            ).associateBy { it.route }
    }

    val root = object : NavGraphSpec {
        override val route: String = "root"

        override val startRoute: Route = agenda

        override val destinationsByRoute: Map<String, DestinationSpec<*>> = emptyMap()

        override val nestedNavGraphs: List<NavGraphSpec> = listOf(
            agenda,
            createTask,
            settings,
        )
    }
}

@Stable
@Composable
fun NavController.currentScreenAsState(): State<NavGraphSpec> {
    val selectedItem = remember { mutableStateOf(NavGraphs.agenda) }

    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedItem.value = destination.navGraph()
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}

fun NavDestination.navGraph(): NavGraphSpec {
    hierarchy.forEach { destination ->
        NavGraphs.root.nestedNavGraphs.forEach { navGraph ->
            if (destination.route == navGraph.route) {
                return navGraph
            }
        }
    }

    throw RuntimeException("Unknown nav graph for destination $route")
}

fun DestinationScope<*>.currentNavigator(): AppNavigator {
    return AppNavigator(
        destination,
        navController,
    )
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    DestinationsNavHost(
        engine = rememberAnimatedNavHostEngine(),
        navController = navController,
        navGraph = NavGraphs.root,
        modifier = modifier,
        dependenciesContainerBuilder = {
            dependency(currentNavigator())
        },
    )
}
