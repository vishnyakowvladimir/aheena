package com.example.feature_itunes.presentation.itunes.mvi.model

internal sealed interface ItunesSideEffect {

    sealed interface Ui : ItunesSideEffect {
        data object Back : Ui
    }

    sealed interface Domain : ItunesSideEffect
}


