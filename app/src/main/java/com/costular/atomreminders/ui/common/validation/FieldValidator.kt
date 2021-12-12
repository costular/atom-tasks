package com.costular.atomreminders.ui.common.validation

interface FieldValidator {

    fun validate(
        input: String,
        vararg textFieldValidation: EmptyFieldValidation,
    ): TextFieldState
}