package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.feature_authentication.biometric.BiometricPromptHandler
import com.example.feature_authentication.biometric.model.BiometricAuthenticationResult
import com.example.lib_ui.components.keyboard.model.PinKey

internal sealed interface PinEvent {

    data object None : PinEvent

    sealed interface Ui : PinEvent {
        data object OnBackPressed : Ui
        data object OnOpenTechClick : Ui
        data class OnKeyboardClick(val key: PinKey) : Ui
        data object OnEnableBiometricsNeeded : Ui
        data class OnBiometricsShowed(val biometricPromptHandler: BiometricPromptHandler) : Ui
        data class OnBiometricsResult(val result: BiometricAuthenticationResult) : Ui
        data object OnLogoutClick : Ui
    }

    sealed interface Domain : PinEvent {
        data class OnAuthentication(val isSuccess: Boolean) : Domain
    }
}
