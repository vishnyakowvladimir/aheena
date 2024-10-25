package com.example.feature_authentication.presentation.create_pin.mvi.model

internal sealed interface CreatePinSideEffect {

    sealed interface Ui : CreatePinSideEffect {
        data object Back : Ui
        data object DelayBeforeChangeMode: Ui
    }

    sealed interface Domain : CreatePinSideEffect {

    }
}


