package com.costular.commonui.components.createtask

import com.costular.atomtasks.core_ui.mvi.UiEvent

sealed class CreateTaskUiEvents : UiEvent {

    data class SaveTask(
        val taskResult: CreateTaskResult,
    ) : CreateTaskUiEvents()
}
