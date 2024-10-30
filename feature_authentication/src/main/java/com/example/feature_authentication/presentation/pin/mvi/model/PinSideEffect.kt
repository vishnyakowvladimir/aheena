package com.example.feature_authentication.presentation.pin.mvi.model

internal sealed interface PinSideEffect {

    sealed interface Ui : PinSideEffect {
        data object Back : Ui
        data object OpenMainScreen : Ui
    }

    sealed interface Domain : PinSideEffect {}
}


