package com.costular.atomtasks.ui.common.validation

data class TextFieldState(
    val value: String = "",
    val errors: List<TextFieldError> = emptyList(),
) {

    val hasError: Boolean
        get() = errors.isNotEmpty()

    companion object {
        val Empty: TextFieldState = TextFieldState()
    }

}