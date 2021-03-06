package com.costular.atomtasks.ui.common.validation

object EmptyFieldValidation : TextFieldValidation<EmptyError> {
    override fun validate(input: String): EmptyError? =
        if (input.isNotEmpty()) {
            null
        } else {
            EmptyError
        }
}

object EmptyError : TextFieldError
