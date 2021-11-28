package com.costular.atomreminders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.costular.atomreminders.ui.features.tasks.detail.TaskDetail
import com.costular.atomreminders.ui.features.agenda.Agenda
import com.costular.atomreminders.ui.theme.AtomRemindersTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtomRemindersTheme {
                Navigation()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Agenda.route) {
        composable(route = Screen.Agenda.route) {
            Agenda(
                onCreateTask = {
                    // TODO:
                },
                onOpenTask = { task ->
                    navController.navigate(Screen.TaskDetail.createRoute(task.id))
                }
            )
        }
        composable(route = Screen.TaskDetail.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("id")
            requireNotNull(taskId) { "id parameter wasn't found. Please make sure it's set!" }
            TaskDetail(taskId.toLong(), onGoBack = { navController.popBackStack() })
        }
    }
}

sealed class Screen(val route: String) {
    object Agenda : Screen("agenda")

    object TaskDetail : Screen("tasks/{id}") {
        fun createRoute(id: Long): String = "tasks/$id"
    }
}