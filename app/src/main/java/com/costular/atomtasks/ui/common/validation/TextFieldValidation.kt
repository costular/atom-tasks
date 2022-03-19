package com.costular.atomtasks.ui.common.validation

interface TextFieldValidation<T : TextFieldError> {

    fun validate(input: String): T?
}
