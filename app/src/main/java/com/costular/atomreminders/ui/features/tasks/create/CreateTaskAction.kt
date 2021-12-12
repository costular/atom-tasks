package com.costular.atomreminders.ui.features.tasks.create

sealed class CreateTaskAction {

    object CreateTask : CreateTaskAction()

    object NavigateUp : CreateTaskAction()

    object SelectDate : CreateTaskAction()

    data class UpdateName(val name: String) : CreateTaskAction()

    object HideKeyboard : CreateTaskAction()

}