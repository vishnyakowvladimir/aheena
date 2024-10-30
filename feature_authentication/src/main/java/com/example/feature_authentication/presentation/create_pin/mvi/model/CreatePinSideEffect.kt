package com.example.feature_authentication.presentation.create_pin.mvi.model

internal sealed interface CreatePinSideEffect {

    sealed interface Ui : CreatePinSideEffect {
        data object Back : Ui
        data object DelayBeforeChangeMode : Ui
        data object OpenMainScreen : Ui
        data object OpenBiometricScreen : Ui
    }

    sealed interface Domain : CreatePinSideEffect {
        data class SaveRefreshToken(val list: List<Int>) : Domain
    }
}


