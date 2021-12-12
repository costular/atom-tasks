package com.costular.atomreminders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.costular.atomreminders.ui.features.tasks.detail.TaskDetail
import com.costular.atomreminders.ui.features.agenda.Agenda
import com.costular.atomreminders.ui.features.tasks.create.CreateTask
import com.costular.atomreminders.ui.theme.AtomRemindersTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@ExperimentalComposeUiApi
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

@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
private fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Agenda.route) {
        composable(route = Screen.Agenda.route) {
            Agenda(
                onCreateTask = { date ->
                    navController.navigate(Screen.TaskCreation.createRoute(date))
                },
                onOpenTask = { task ->
                    navController.navigate(Screen.TaskDetail.createRoute(task.id))
                }
            )
        }
        composable(route = Screen.TaskDetail.route) { navBackStackEntry ->
            val taskId = navBackStackEntry.arguments?.getString("id")
            requireNotNull(taskId) { "id parameter wasn't found. Please make sure it's set!" }
            TaskDetail(taskId.toLong(), onGoBack = { navController.popBackStack() })
        }
        composable(route = Screen.TaskCreation.route) { navBackStackEntry ->
            val dateString = navBackStackEntry.arguments?.getString("date")
            val date = if (dateString != null) {
                LocalDate.parse(dateString)
            } else {
                null
            }

            CreateTask(
                onNavigateBack = { navController.navigateUp() },
                date = date,
            )
        }
    }
}

sealed class Screen(val route: String) {
    object Agenda : Screen("agenda")

    object TaskDetail : Screen("tasks/{id}") {
        fun createRoute(id: Long): String = "tasks/$id"
    }

    object TaskCreation : Screen("create_task?date={date}") {
        fun createRoute(date: LocalDate?): String = "create_task?date=$date"
    }
}