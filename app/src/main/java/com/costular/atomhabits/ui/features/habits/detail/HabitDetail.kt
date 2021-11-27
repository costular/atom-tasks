package com.costular.atomhabits.ui.features.habits.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomhabits.domain.Async
import com.costular.atomhabits.domain.model.Daily
import com.costular.atomhabits.domain.model.Habit
import com.costular.atomhabits.domain.model.Reminder
import com.costular.atomhabits.ui.components.*
import com.costular.atomhabits.ui.theme.AtomHabitsTheme
import com.costular.atomhabits.ui.util.rememberFlowWithLifecycle
import kotlinx.coroutines.flow.collect


@Composable
fun HabitDetail(
    habitId: Long,
    onGoBack: () -> Unit
) {
    val viewModel: HabitDetailViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(HabitDetailState())

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is HabitDetailEvents.GoBack -> onGoBack()
            }
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.load(habitId)
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
    state: HabitDetailState
) {
    when (val habit = state.habit) {
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
    habit: Habit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        ScreenHeader(
            text = habit.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.CalendarToday,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = repetitionAsText(habit.repetition),
                    style = MaterialTheme.typography.body1
                )
            }

            if (habit.reminder != null) {
                Spacer(modifier = Modifier.width(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Alarm,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = reminderAsText(habit.reminder),
                        style = MaterialTheme.typography.body1
                    )
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