package com.costular.atomreminders.ui.features.agenda

import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Task
import com.costular.atomreminders.ui.features.agenda.AgendaViewModel.Companion.DaysAfter
import com.costular.atomreminders.ui.features.agenda.AgendaViewModel.Companion.DaysBefore
import java.time.LocalDate

data class AgendaState(
    val selectedDay: LocalDate = LocalDate.now(),
    val tasks: Async<List<Task>> = Async.Uninitialized,
) {
    val calendarFromDate = LocalDate.now().minusDays(DaysBefore.toLong())
    val calendarUntilDate = LocalDate.now().plusDays(DaysAfter.toLong())

    val isPreviousDaySelected get() = calendarFromDate.isBefore(selectedDay)
    val isNextDaySelected get() = calendarUntilDate.isAfter(selectedDay)

}