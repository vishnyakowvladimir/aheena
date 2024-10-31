package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core.crypto.rsa.model.AuthenticationCryptoObject

internal sealed interface PinUiCommand {
    data class ShowBiometricsDialog(val cryptoObject: AuthenticationCryptoObject) : PinUiCommand
}
