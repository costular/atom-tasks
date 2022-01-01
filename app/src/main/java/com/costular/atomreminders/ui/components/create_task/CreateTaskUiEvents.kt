package com.costular.atomreminders.ui.components.create_task

import com.costular.decorit.presentation.base.UiEvent

sealed class CreateTaskUiEvents : UiEvent {

    data class SaveTask(
        val taskResult: CreateTaskResult
    ) : CreateTaskUiEvents()

}