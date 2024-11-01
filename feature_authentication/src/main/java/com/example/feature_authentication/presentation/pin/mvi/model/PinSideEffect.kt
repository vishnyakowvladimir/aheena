package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core.crypto.rsa.model.AuthenticationCryptoObject

internal sealed interface PinSideEffect {

    sealed interface Ui : PinSideEffect {
        data object Back : Ui
        data object OpenMainScreen : Ui
    }

    sealed interface Domain : PinSideEffect {
        data class AuthenticationByPinNeeded(val pin: CharSequence) : Domain
        data class AuthenticationByBiometricNeeded(val cryptoObject: AuthenticationCryptoObject) : Domain
    }
}


