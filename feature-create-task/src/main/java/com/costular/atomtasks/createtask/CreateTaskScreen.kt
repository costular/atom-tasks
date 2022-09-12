package com.costular.atomtasks.createtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomtasks.coreui.utils.rememberFlowWithLifecycle
import com.costular.atomtasks.createtask.CreateTaskState.Companion.Empty
import com.costular.commonui.components.createtask.CreateTaskExpanded
import com.costular.core.Async
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import java.time.LocalDate

@Destination(style = DestinationStyle.BottomSheet::class)
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
            .navigationBarsWithImePadding(),
    )
}
