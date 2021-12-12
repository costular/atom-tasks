package com.costular.atomreminders.ui.features.tasks.detail

import com.costular.decorit.presentation.base.UiEvent

sealed class TaskDetailEvents : UiEvent {

    object GoBack : TaskDetailEvents()

}