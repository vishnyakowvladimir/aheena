package com.example.feature_authentication.presentation.biometric.mvi.model

internal sealed interface BiometricsEvent {

    sealed interface Ui : BiometricsEvent {
        data object None : Ui
        data object OnBackPressed : Ui
        data object OnEnableButtonClick : Ui
        data object OnSkipButtonClick : Ui
    }

    sealed interface Domain : BiometricsEvent {
        data object None : Domain
        data object OnPinCodeSaved : Domain
    }
}
