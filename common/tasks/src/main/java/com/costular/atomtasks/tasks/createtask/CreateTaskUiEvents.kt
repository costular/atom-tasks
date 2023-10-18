package com.costular.atomtasks.tasks.createtask

import com.costular.atomtasks.core.ui.mvi.UiEvent

sealed interface CreateTaskUiEvents : UiEvent {

    data class SaveTask(
        val taskResult: CreateTaskResult,
    ) : CreateTaskUiEvents

    object NavigateToExactAlarmSettings : CreateTaskUiEvents

}
