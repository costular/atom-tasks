package com.costular.atomtasks.agenda.ui

import com.costular.atomtasks.core.ui.mvi.UiEvent
import java.time.LocalDate

sealed interface AgendaUiEvents : UiEvent {
    data class GoToNewTaskScreen(
        val date: LocalDate,
        val shouldShowNewScreen: Boolean,
    ) : AgendaUiEvents

    data class GoToEditScreen(
        val taskId: Long,
        val shouldShowNewScreen: Boolean,
    ) : AgendaUiEvents

    data object OpenOnboarding : UiEvent
}
