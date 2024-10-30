package com.example.feature_authentication.presentation.biometric.mvi.model

internal sealed interface BiometricsSideEffect {

    sealed interface Ui : BiometricsSideEffect {
        data object Back : Ui
        data object OpenMainScreen : Ui
    }

    sealed interface Domain : BiometricsSideEffect {
        data object SavePinCode : Domain
    }
}


