package com.example.feature_authentication.presentation.login.model

import androidx.compose.ui.text.input.TextFieldValue

internal data class LoginUiState(
    val phoneState: PhoneState,
    val passwordState: PasswordState,
    val confirmButtonsState: ConfirmButtonState,
) {
    data class PhoneState(
        val textFieldValue: TextFieldValue,
        val placeholderText: String,
        val errorText: String,
        val showError: Boolean,
    )

    data class PasswordState(
        val textFieldValue: TextFieldValue,
        val placeholderText: String,
        val isPasswordVisible: Boolean,
        val errorText: String,
        val showError: Boolean,
    )

    data class ConfirmButtonState(
        val title: String,
        val isEnabled: Boolean,
    )
}
