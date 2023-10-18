package com.costular.atomtasks.createtask

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.costular.atomtasks.tasks.createtask.CreateTaskExpanded
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
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is CreateTaskState.Success) {
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
