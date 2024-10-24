package com.example.feature_authentication.presentation.phone_and_password.mvi.model

import androidx.compose.ui.text.input.TextFieldValue

internal data class LoginDomainState(
    val phoneState: PhoneState,
    val passwordState: PasswordState,
    val confirmButtonsState: ConfirmButtonState,
) {
    data class PhoneState(
        val textFieldValue: TextFieldValue,
        val isError: Boolean,
    )

    data class PasswordState(
        val textFieldValue: TextFieldValue,
        val isPasswordVisible: Boolean,
        val isError: Boolean,
    )

    data class ConfirmButtonState(
        val isEnabled: Boolean,
    )
}
