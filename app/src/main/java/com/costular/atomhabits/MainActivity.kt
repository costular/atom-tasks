package com.costular.atomhabits

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.costular.atomhabits.ui.features.habits.create.CreateHabit
import com.costular.atomhabits.ui.features.habits.list.HabitsScreen
import com.costular.atomhabits.ui.theme.AtomHabitsTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AtomHabitsTheme {
                Navigation()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
private fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = Screen.HabitList.route) {
        composable(route = Screen.HabitList.route) {
            HabitsScreen(
                onCreateHabit = {
                    navController.navigate(Screen.NewHabit.route)
                },
                onOpenHabit = {
                    // TODO: 22/6/21
                }
            )
        }
        composable(route = Screen.NewHabit.route) {
            CreateHabit(
                goBack = { navController.popBackStack() }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object HabitList : Screen("habits")
    object NewHabit : Screen("habits/new")
}