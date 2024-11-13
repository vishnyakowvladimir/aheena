package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.core_api.crypto.rsa.cipher.model.CipherHolder

internal sealed interface PinUiCommand {
    data class ShowBiometricsDialog(val cryptoObject: CipherHolder) : PinUiCommand
}
