package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core_api.crypto.rsa.cipher.model.CipherHolder

internal sealed interface PinSideEffect {

    sealed interface Ui : PinSideEffect {
        data object Back : Ui
        data object Logout : Ui
        data object OpenTechScreen : Ui
        data object OpenMainScreen : Ui
    }

    sealed interface Domain : PinSideEffect {
        data class AuthenticationByPinNeeded(val pin: CharSequence) : Domain
        data class AuthenticationByBiometricNeeded(val cipher: CipherHolder) : Domain
    }
}


