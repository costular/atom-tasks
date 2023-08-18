package com.costular.atomtasks.createtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.core.ui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.createtask.CreateTaskState.Companion.Empty
import com.costular.designsystem.components.createtask.CreateTaskExpanded
import com.costular.core.Async
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyleBottomSheet
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Destination(style = DestinationStyleBottomSheet::class)
@Composable
fun CreateTaskScreen(
    text: String?,
    date: String?,
    navigator: DestinationsNavigator,
) {
    val viewModel: CreateTaskViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(Empty)

    LaunchedEffect(state.savingTask) {
        if (state.savingTask is Async.Success<*>) {
            navigator.navigateUp()
        }
    }

    val localDate = remember(date) {
        if (date != null) {
            LocalDate.parse(date)
        } else {
            LocalDate.now()
        }
    }

    CreateTaskExpanded(
        value = text ?: "",
        date = localDate,
        onSave = { result ->
            viewModel.createTask(
                result.name,
                result.date,
                result.reminder,
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding(),
    )
}