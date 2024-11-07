package com.example.feature_authentication.presentation.biometric.mvi.model

internal sealed interface BiometricsEvent {

    data object None : BiometricsEvent

    sealed interface Ui : BiometricsEvent {
        data object OnBackPressed : Ui
        data object OnEnableButtonClick : Ui
        data object OnSkipButtonClick : Ui
    }

    sealed interface Domain : BiometricsEvent {
        data object OnPinCodeSaved : Domain
    }
}
