package com.costular.atomtasks.ui.home

import androidx.navigation.NavController
import com.atomtasks.feature.detail.destinations.TaskDetailScreenDestination
import com.costular.atomtasks.agenda.ui.AgendaNavigator
import com.costular.atomtasks.agenda.destinations.TasksActionsBottomSheetDestination
import com.costular.atomtasks.createtask.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.feature.edittask.destinations.EditTaskScreenDestination
import com.costular.atomtasks.settings.SettingsNavigator
import com.costular.atomtasks.settings.destinations.ThemeSelectorScreenDestination
import com.ramcosta.composedestinations.navigation.navigate
import java.time.LocalDate

class AppNavigator(
    private val navController: NavController,
) : SettingsNavigator, AgendaNavigator {

    override fun navigateToCreateTask(date: String) {
        navController.navigate(CreateTaskScreenDestination(date))
    }

    override fun navigateToEditTask(taskId: Long) {
        navController.navigate(EditTaskScreenDestination(taskId))
    }

    override fun navigateToDetailScreenForCreateTask(date: String) {
        navController.navigate(
            TaskDetailScreenDestination(
                defaultDate = LocalDate.parse(date),
                taskId = null,
            )
        )
    }

    override fun navigateToDetailScreenToEdit(taskId: Long) {
        navController.navigate(
            TaskDetailScreenDestination(
                defaultDate = null,
                taskId = taskId
            )
        )
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
