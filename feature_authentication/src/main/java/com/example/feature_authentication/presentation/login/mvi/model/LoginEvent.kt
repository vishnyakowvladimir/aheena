package com.example.feature_authentication.presentation.login.mvi.model

import androidx.compose.ui.text.input.TextFieldValue

internal sealed interface LoginEvent {

    data object None : LoginEvent

    sealed interface Ui : LoginEvent {
        data object OnBackPressed : Ui
        data class OnPhoneChanged(val value: TextFieldValue) : Ui
        data object OnResetPhoneIconClick : Ui
        data class OnPasswordChanged(val value: TextFieldValue) : Ui
        data object OnResetPasswordIconClick : Ui
        data object OnPasswordIconClick : Ui
        data object OnConfirmButtonClick : Ui
    }

    sealed interface Domain : LoginEvent
}
