package xyz.c3rberuss.fields_validator

abstract class InputValidation(open val errorMessage: String) {
    abstract suspend fun validate(value: String?): Boolean
}

sealed class Validators(errorMessage: String) : InputValidation(errorMessage) {
    class MinLength(private val minLength: Int, override val errorMessage: String = "") :
        Validators(errorMessage) {
        override suspend fun validate(value: String?): Boolean {
            return !value.isNullOrBlank() && value.length >= minLength
        }
    }

    class MaxLength(
        private val maxLength: Int,
        private val inclusive: Boolean = true,
        override val errorMessage: String = ""
    ) :
        Validators(errorMessage) {
        override suspend fun validate(value: String?): Boolean {
            return !value.isNullOrBlank() && (if (inclusive) value.length <= maxLength else value.length < maxLength)
        }
    }

    class LengthInRange(
        private val minLength: Int,
        private val maxLength: Int,
        private val inclusive: Boolean = true,
        override val errorMessage: String = ""
    ) : Validators(errorMessage) {
        override suspend fun validate(value: String?): Boolean {
            return !value.isNullOrBlank() && value.length >= minLength && (if (inclusive) value.length <= maxLength else value.length < maxLength)
        }
    }

    class RegularExpression(private val pattern: String, override val errorMessage: String = "") :
        Validators(errorMessage) {
        override suspend fun validate(value: String?): Boolean {
            val regEx = Regex(pattern)

            return value != null && regEx.matches(value)
        }
    }

    class Required(override val errorMessage: String = "") : Validators(errorMessage) {
        override suspend fun validate(value: String?): Boolean {
            return !value.isNullOrBlank()
        }
    }
}