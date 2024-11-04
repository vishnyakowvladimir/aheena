package com.example.feature_main.presentation.main.mvi.model

internal sealed interface MainSideEffect {

    sealed interface Ui : MainSideEffect {
        data object Back : Ui
    }

    sealed interface Domain : MainSideEffect
}


