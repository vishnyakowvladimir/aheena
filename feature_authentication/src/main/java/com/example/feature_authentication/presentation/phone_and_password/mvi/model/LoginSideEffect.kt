package com.example.feature_authentication.presentation.phone_and_password.mvi.model

internal sealed interface LoginSideEffect {

    sealed interface Ui : LoginSideEffect {
        data object Back : Ui
        data object OpenPinScreen : Ui
    }

    sealed interface Domain : LoginSideEffect {

    }
}


