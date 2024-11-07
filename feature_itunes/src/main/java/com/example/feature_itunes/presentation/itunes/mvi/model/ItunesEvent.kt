package com.example.feature_itunes.presentation.itunes.mvi.model

internal sealed interface ItunesEvent {

    data object None : ItunesEvent

    sealed interface Ui : ItunesEvent {
        data object OnBackPressed : Ui
    }

    sealed interface Domain : ItunesEvent
}
