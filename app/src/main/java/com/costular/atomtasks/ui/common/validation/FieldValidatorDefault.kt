package com.costular.atomtasks.ui.common.validation

class FieldValidatorDefault : FieldValidator {

    override fun validate(
        input: String,
        vararg textFieldValidation: EmptyFieldValidation,
    ): TextFieldState {
        return TextFieldState(
            input,
            textFieldValidation.mapNotNull { it.validate(input) }
        )
    }

}