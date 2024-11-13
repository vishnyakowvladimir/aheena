package com.example.feature_authentication.presentation.pin.model

import com.example.lib_ui.components.pincode.model.PinCodeFieldState

data class PinUiState(
    val pinCodeFieldState: PinCodeFieldState,
    val withBiometrics: Boolean,
    val isTechVisible: Boolean,
)
