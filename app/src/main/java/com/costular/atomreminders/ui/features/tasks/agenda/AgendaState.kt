package com.costular.atomreminders.ui.features.tasks.agenda

import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Task
import java.time.LocalDate

data class AgendaState(
    val selectedDay: LocalDate = LocalDate.now(),
    val habits: Async<List<Task>> = Async.Uninitialized
)