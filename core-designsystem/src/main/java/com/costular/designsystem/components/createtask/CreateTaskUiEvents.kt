package com.costular.designsystem.components.createtask

import com.costular.atomtasks.coreui.mvi.UiEvent

sealed class CreateTaskUiEvents : UiEvent {

    data class SaveTask(
        val taskResult: CreateTaskResult,
    ) : CreateTaskUiEvents()
}