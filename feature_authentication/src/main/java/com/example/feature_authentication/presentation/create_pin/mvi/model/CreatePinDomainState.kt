package com.example.feature_authentication.presentation.create_pin.mvi.model

internal data class CreatePinDomainState(
    val createPinState: CreatePinState,
    val confirmPinState: ConfirmPinState,
    val mode: Mode = Mode.CREATE,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
) {
    data class CreatePinState(
        val pin: List<Int>,
    )

    data class ConfirmPinState(
        val pin: List<Int>,
    )

    enum class Mode {
        CREATE, CONFIRM;
    }
}
