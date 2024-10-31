package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core.crypto.rsa.model.AuthenticationCryptoObject

internal data class PinDomainState(
    val pin: List<Int>,
    val cryptoObject: AuthenticationCryptoObject?,
    val isBiometricsReady: Boolean,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
