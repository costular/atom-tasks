package com.costular.atomtasks.ui.features.edittask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.core.ui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.core.ui.R
import com.costular.designsystem.components.createtask.CreateTaskExpanded
import com.costular.designsystem.dialogs.AtomSheet
import com.costular.core.Async
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet

@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun EditTaskScreen(
    taskId: Long,
    navigator: DestinationsNavigator,
    viewModel: EditTaskViewModel = hiltViewModel(),
) {
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(EditTaskState.Empty)
    val savingTask = state.savingTask

    LaunchedEffect(savingTask) {
        if (savingTask is Async.Success<*>) {
            navigator.navigateUp()
        }
    }

    LaunchedEffect(taskId) {
        viewModel.loadTask(taskId)
    }

    val task = state.taskState
    if (task is TaskState.Success) {
        AtomSheet(
            title = stringResource(R.string.agenda_edit_task),
            onNavigateUp = { navigator.navigateUp() },
        ) {
            CreateTaskExpanded(
                value = task.name,
                date = task.date,
                onSave = { result ->
                    viewModel.editTask(
                        result.name,
                        result.date,
                        result.reminder,
                    )
                },
                reminder = task.reminder,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .imePadding(),
            )
        }
    }
}
