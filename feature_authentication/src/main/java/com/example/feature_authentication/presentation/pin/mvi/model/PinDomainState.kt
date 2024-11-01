package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core.crypto.rsa.cipher.model.AuthenticationCryptoObject
import com.example.feature_authentication.biometric.BiometricPromptHandler

internal data class PinDomainState(
    val pin: List<Int>,
    val cryptoObject: AuthenticationCryptoObject?,
    val biometricPromptHandler: BiometricPromptHandler?,
    val isBiometricsReady: Boolean,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
