package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core_api.crypto.rsa.cipher.model.CipherHolder
import com.example.feature_authentication.biometric.BiometricPromptHandler

internal data class PinDomainState(
    val pin: List<Int>,
    val cipher: CipherHolder?,
    val biometricPromptHandler: BiometricPromptHandler?,
    val isBiometricsReady: Boolean,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
