package com.costular.atomreminders.ui.features.tasks.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Task
import com.costular.atomreminders.ui.components.*
import com.costular.atomreminders.ui.util.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect


@Composable
fun TaskDetail(
    taskId: Long,
    onGoBack: () -> Unit
) {
    val viewModel: TaskDetailViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(TaskDetailState())

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is TaskDetailEvents.GoBack -> onGoBack()
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load(taskId)
    }

    var showRemoveDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.Edit, contentDescription = null)
                    }
                    IconButton(onClick = { showRemoveDialog = true }) {
                        Icon(Icons.Outlined.Delete, contentDescription = null)
                    }
                }
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box {
            HabitDetailContent(state)

            if (showRemoveDialog) {
                RemoveHabitDialog(
                    onAccept = {
                        showRemoveDialog = false
                        viewModel.delete()
                    },
                    onCancel = { showRemoveDialog = false }
                )
            }
        }
    }
}

@Composable
private fun HabitDetailContent(
    state: TaskDetailState
) {
    when (val habit = state.task) {
        is Async.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is Async.Success -> {
            HabitContent(habit.data)
        }
    }
}

@Composable
private fun HabitContent(
    task: Task
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            text = task.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            if (task.reminder != null) {
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Icon(
                            Icons.Outlined.Alarm,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)

                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = reminderAsText(task.reminder),
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RemoveHabitDialog(
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        text = {
            Text("Are you sure to remove this habit?")
        },
        confirmButton = {
            Button(onClick = onAccept) {
                Text("Remove")
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    )
}