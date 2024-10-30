package com.example.feature_authentication.presentation.pin.mvi.model

internal data class PinDomainState(
    val pin: List<Int>,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
