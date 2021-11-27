package com.costular.atomreminders.ui.features.tasks.agenda

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.costular.atomreminders.ui.components.HorizontalCalendar
import java.time.LocalDate
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Task
import com.costular.atomreminders.ui.components.HabitList
import com.costular.atomreminders.ui.components.ScreenHeader
import com.costular.atomreminders.ui.util.DateUtils.dayAsText
import com.costular.atomreminders.ui.util.rememberFlowWithLifecycle

@Composable
fun Agenda(
    onCreateTask: () -> Unit,
    onOpenTask: (Task) -> Unit
) {
    val viewModel: AgendaViewModel = hiltViewModel()
    val state by rememberFlowWithLifecycle(viewModel.state).collectAsState(initial = AgendaState())

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = {
                    Text("Create")
                },
                onClick = onCreateTask,
                icon = {
                    Icon(imageVector = Icons.Outlined.Add, contentDescription = null)
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val selectedDayText = dayAsText(state.selectedDay)
                ScreenHeader(
                    text = selectedDayText,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 24.dp, horizontal = 16.dp)
                        .clickable {
                            // TODO: 26/6/21 open calendar
                        }
                )

                IconButton(
                    onClick = {
                        val newDay = state.selectedDay.minusDays(1)
                        viewModel.setSelectedDay(newDay)
                    },
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ) {
                    Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = null)
                }
                IconButton(
                    onClick = {
                        val newDay = state.selectedDay.plusDays(1)
                        viewModel.setSelectedDay(newDay)
                    },
                    modifier = Modifier
                        .width(40.dp)
                        .height(40.dp)
                ) {
                    Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
                }
            }

            HorizontalCalendar(
                from = LocalDate.now().minusDays(7),
                until = LocalDate.now().plusDays(7),
                selectedDay = state.selectedDay,
                modifier = Modifier.padding(bottom = 24.dp),
                onSelectDay = {
                    viewModel.setSelectedDay(it)
                }
            )

            val habits = state.habits
            when (habits) {
                is Async.Success -> {
                    HabitList(
                        tasks = habits.data,
                        onClick = { onOpenTask(it) },
                        onMarkHabit = { id, isMarked -> viewModel.onMarkTask(id, !isMarked) },
                        modifier = Modifier.fillMaxSize(),
                        date = state.selectedDay
                    )
                }
            }

        }
    }
}