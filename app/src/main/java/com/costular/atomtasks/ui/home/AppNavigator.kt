package com.costular.atomtasks.ui.home

import androidx.navigation.NavController
import com.costular.atomtasks.agenda.AgendaNavigator
import com.costular.atomtasks.agenda.destinations.TasksActionsBottomSheetDestination
import com.costular.atomtasks.createtask.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.settings.SettingsNavigator
import com.costular.atomtasks.settings.destinations.ThemeSelectorScreenDestination
import com.costular.atomtasks.ui.features.edittask.destinations.EditTaskScreenDestination
import com.ramcosta.composedestinations.navigation.navigate

class AppNavigator(
    private val navController: NavController,
) : SettingsNavigator, AgendaNavigator {

    override fun navigateToCreateTask(date: String, text: String?) {
        navController.navigate(CreateTaskScreenDestination(text = text, date = date))
    }

    override fun navigateToEditTask(taskId: Long) {
        navController.navigate(EditTaskScreenDestination(taskId))
    }

    override fun openTaskActions(taskId: Long, taskName: String, isDone: Boolean) {
        navController.navigate(TasksActionsBottomSheetDestination(taskId, taskName, isDone))
    }

    override fun navigateUp() {
        navController.navigateUp()
    }

    override fun navigateToSelectTheme(theme: String) {
        navController.navigate(ThemeSelectorScreenDestination(theme))
    }
}
