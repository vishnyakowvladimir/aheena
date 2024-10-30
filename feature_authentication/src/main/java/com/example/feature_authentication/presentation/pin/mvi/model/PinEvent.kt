package com.example.feature_authentication.presentation.pin.mvi.model

import com.example.lib_ui.components.keyboard.model.PinKey

internal sealed interface PinEvent {

    sealed interface Ui : PinEvent {
        data object None : Ui
        data object OnBackPressed : Ui
        data class OnKeyboardClick(val key: PinKey) : Ui
    }

    sealed interface Domain : PinEvent {}
}
