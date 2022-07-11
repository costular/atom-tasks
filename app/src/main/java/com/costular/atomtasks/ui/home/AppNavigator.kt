package com.costular.atomtasks.ui.home

import androidx.navigation.NavController
import com.costular.atomtasks.agenda.AgendaNavigator
import com.costular.atomtasks.createtask.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.settings.SettingsNavigator
import com.costular.atomtasks.settings.destinations.ThemeSelectorScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.spec.DestinationSpec

class AppNavigator(
    private val currentDestination: DestinationSpec<*>,
    private val navController: NavController,
) : SettingsNavigator, AgendaNavigator {

    override fun navigateToCreateTask(date: String, text: String?) {
        navController.navigate(CreateTaskScreenDestination(text = text, date = date))
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateToSelectTheme(theme: String) {
        navController.navigate(ThemeSelectorScreenDestination(theme))
    }
}
