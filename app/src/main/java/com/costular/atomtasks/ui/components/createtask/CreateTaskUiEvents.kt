package com.costular.atomtasks.ui.components.createtask

import com.costular.decorit.presentation.base.UiEvent

sealed class CreateTaskUiEvents : UiEvent {

    data class SaveTask(
        val taskResult: CreateTaskResult
    ) : CreateTaskUiEvents()
}
