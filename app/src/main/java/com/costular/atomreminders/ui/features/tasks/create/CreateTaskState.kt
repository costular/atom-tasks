package com.costular.atomreminders.ui.features.tasks.create

import com.costular.atomreminders.ui.common.validation.TextFieldState
import java.time.LocalDate
import java.time.LocalTime

data class CreateTaskState(
    val name: TextFieldState = TextFieldState.Empty,
    val date: LocalDate = LocalDate.now(),
    val reminder: LocalTime? = null,
    val selectDate: Boolean = false,
    val isSaving: Boolean = false,
) {

    val isDateValid: Boolean
        get() = date.isAfter(LocalDate.now()) || date == LocalDate.now()

    val canSave: Boolean
        get() = name.value.isNotEmpty() && !name.hasError && isDateValid

    companion object {
        val Empty = CreateTaskState()
    }
}