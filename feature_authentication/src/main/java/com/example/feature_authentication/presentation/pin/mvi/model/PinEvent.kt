package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.feature_authentication.biometric.BiometricPromptHandler
import com.example.feature_authentication.biometric.model.BiometricAuthenticationResult
import com.example.lib_ui.components.keyboard.model.PinKey

internal sealed interface PinEvent {

    sealed interface Ui : PinEvent {
        data object None : Ui
        data object OnBackPressed : Ui
        data class OnKeyboardClick(val key: PinKey) : Ui
        data object OnEnableBiometricsNeeded : Ui
        data class OnBiometricsShowed(val biometricPromptHandler: BiometricPromptHandler) : Ui
        data class OnBiometricsResult(val result: BiometricAuthenticationResult) : Ui
    }

    sealed interface Domain : PinEvent {
        data class OnAuthentication(val isSuccess: Boolean) : Domain
    }
}
