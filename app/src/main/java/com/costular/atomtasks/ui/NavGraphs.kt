package com.costular.atomtasks.ui

import com.atomtasks.feature.detail.destinations.TaskDetailScreenDestination
import com.costular.atomtasks.agenda.destinations.AgendaScreenDestination
import com.costular.atomtasks.agenda.destinations.TasksActionsBottomSheetDestination
import com.costular.atomtasks.createtask.destinations.CreateTaskScreenDestination
import com.costular.atomtasks.feature.edittask.destinations.EditTaskScreenDestination
import com.costular.atomtasks.settings.destinations.SettingsScreenDestination
import com.costular.atomtasks.settings.destinations.ThemeSelectorScreenDestination
import com.ramcosta.composedestinations.spec.DestinationSpec
import com.ramcosta.composedestinations.spec.NavGraphSpec
import com.ramcosta.composedestinations.spec.Route

object NavGraphs {

    val agenda = object : NavGraphSpec {
        override val route: String = "agenda"

        override val startRoute: Route = AgendaScreenDestination

        override val destinationsByRoute = listOf<DestinationSpec<*>>(
            AgendaScreenDestination,
            TasksActionsBottomSheetDestination,
            TaskDetailScreenDestination,
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
                ThemeSelectorScreenDestination,
            ).associateBy { it.route }
    }

    val editTask = object : NavGraphSpec {
        override val route: String = "edittasks"

        override val startRoute: Route = EditTaskScreenDestination

        override val destinationsByRoute: Map<String, DestinationSpec<*>> =
            listOf<DestinationSpec<*>>(
                EditTaskScreenDestination,
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
            editTask,
        )
    }
}
