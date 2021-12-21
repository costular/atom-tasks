package com.costular.atomreminders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.costular.atomreminders.ui.features.agenda.Agenda
import com.costular.atomreminders.ui.features.tasks.create.CreateTask
import com.costular.atomreminders.ui.theme.AtomRemindersTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
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

@ExperimentalMaterialApi
@ExperimentalComposeUiApi
@Composable
private fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.Agenda.route) {
        composable(route = Screen.Agenda.route) {
            Agenda(
                onCreateTask = { date ->
                    navController.navigate(Screen.TaskCreation.createRoute(date))
                }
            )
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

    object TaskCreation : Screen("create_task?date={date}") {
        fun createRoute(date: LocalDate?): String = "create_task?date=$date"
    }
}