package xyz.c3rberuss.fieldsvalidator

import xyz.c3rberuss.fields_validator.InputValidation


class EqualsToValidator(private val text: String, override val errorMessage: String = "") :
    InputValidation(errorMessage) {
    override suspend fun validate(value: String?): Boolean {
        return value == text
    }
}