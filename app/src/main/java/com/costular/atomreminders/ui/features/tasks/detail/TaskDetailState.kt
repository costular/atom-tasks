package com.costular.atomreminders.ui.features.tasks.detail

import com.costular.atomreminders.domain.Async
import com.costular.atomreminders.domain.model.Task

data class TaskDetailState(
    val task: Async<Task> = Async.Uninitialized
)