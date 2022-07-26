package xyz.c3rberuss.fields_validator

import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun EditText.validate(
    lifecycleOwner: LifecycleOwner,
    validators: List<InputValidation>,
    showError: Boolean = false,
    onTextChanged: (text: String) -> Unit = {},
    isValidField: (isValid: Boolean, errorMessage: String?) -> Unit,
) {

    var validateJob: Job? = null

    doOnTextChanged { value, _, _, _ ->
        validateJob?.cancel()

        validateJob = lifecycleOwner.lifecycleScope.launch {

            var validFieldsCounter = 0
            onTextChanged(value?.toString().orEmpty())

            validators.forEach { validator ->
                val isValid = validator.validate(value?.toString())

                if (isValid) {
                    validFieldsCounter++
                } else {
                    isValidField(false, validator.errorMessage)
                    if (showError) error = validator.errorMessage
                    return@launch
                }
            }

            isValidField(validFieldsCounter >= validators.size, null)
            if (showError) error = null
        }
    }
}


fun EditText.validate(
    lifecycleOwner: LifecycleOwner,
    validator: InputValidation,
    showError: Boolean = false,
    onTextChanged: (text: String) -> Unit = {},
    isValidField: (isValid: Boolean, errorMessage: String?) -> Unit,
) {

    var validateJob: Job? = null

    doOnTextChanged { value, _, _, _ ->
        validateJob?.cancel()

        validateJob = lifecycleOwner.lifecycleScope.launch {

            onTextChanged(value?.toString().orEmpty())

            if (!validator.validate(value?.toString())) {
                isValidField(false, validator.errorMessage)
                return@launch
            }

            isValidField(true, null)
        }
    }
}