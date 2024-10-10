package com.costular.atomtasks.ui.home

import androidx.navigation.NavController
import com.costular.atomtasks.agenda.ui.AgendaNavigator
import com.costular.atomtasks.settings.SettingsNavigator
import com.ramcosta.composedestinations.generated.agenda.destinations.TasksActionsBottomSheetDestination
import com.ramcosta.composedestinations.generated.detail.destinations.TaskDetailScreenDestination
import com.ramcosta.composedestinations.generated.settings.destinations.ThemeSelectorScreenDestination
import com.ramcosta.composedestinations.utils.toDestinationsNavigator
import java.time.LocalDate

class AppNavigator(
    private val navController: NavController,
) : SettingsNavigator, AgendaNavigator {

    private val destinationsNavigator by lazy {
        navController.toDestinationsNavigator()
    }

    override fun navigateToDetailScreenForCreateTask(date: String) {
        destinationsNavigator.navigate(
            TaskDetailScreenDestination(
                defaultDate = LocalDate.parse(date),
                taskId = null,
            )
        )
    }

    override fun navigateToDetailScreenToEdit(taskId: Long) {
        destinationsNavigator.navigate(
            TaskDetailScreenDestination(
                defaultDate = null,
                taskId = taskId
            )
        )
    }

    override fun openTaskActions(taskId: Long, taskName: String, isDone: Boolean) {
        destinationsNavigator.navigate(TasksActionsBottomSheetDestination(taskId, taskName, isDone))
    }

    override fun navigateUp() {
        destinationsNavigator.navigateUp()
    }

    override fun navigateToSelectTheme(theme: String) {
        destinationsNavigator.navigate(ThemeSelectorScreenDestination(theme))
    }
}
