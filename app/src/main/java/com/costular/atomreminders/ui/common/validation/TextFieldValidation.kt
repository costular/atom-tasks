package com.costular.atomreminders.ui.common.validation

interface TextFieldValidation<T : TextFieldError> {

    fun validate(input: String): T?
}