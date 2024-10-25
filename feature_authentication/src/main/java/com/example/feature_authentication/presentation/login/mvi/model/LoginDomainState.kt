package com.example.feature_authentication.presentation.login.mvi.model

import androidx.compose.ui.text.input.TextFieldValue

private const val PHONE_NUMBER_SIZE = 10
private const val PASSWORD_SIZE = 6

internal data class LoginDomainState(
    val phoneState: PhoneState,
    val passwordState: PasswordState,
    val confirmButtonsState: ConfirmButtonState,
) {
    data class PhoneState(
        val textFieldValue: TextFieldValue,
        val showError: Boolean,
    ) {
        fun isFilled(): Boolean {
            return textFieldValue.text.length == PHONE_NUMBER_SIZE
        }
    }

    data class PasswordState(
        val textFieldValue: TextFieldValue,
        val isPasswordVisible: Boolean,
        val showError: Boolean,
    ) {

        fun isNotEmpty(): Boolean {
            return textFieldValue.text.isNotEmpty()
        }

        fun isCorrect(): Boolean {
            return textFieldValue.text.length >= PASSWORD_SIZE
        }
    }

    data class ConfirmButtonState(
        val isEnabled: Boolean,
    )

    fun isCorrect(): Boolean {
        return phoneState.isFilled() && passwordState.isCorrect()
    }
}
