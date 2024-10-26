package com.example.feature_authentication.presentation.create_pin.mvi.model

import com.example.lib_ui.components.keyboard.model.PinKey

internal sealed interface CreatePinEvent {

    sealed interface Ui : CreatePinEvent {
        data object None : Ui
        data object OnBackPressed : Ui
        data class OnKeyboardClick(val key: PinKey) : Ui
        data object OnDelayBeforeChangeMode : Ui
    }

    sealed interface Domain : CreatePinEvent {
        data object OnPinCodeSaved: Domain
    }
}
