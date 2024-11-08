package com.example.feature_itunes.presentation.itunes.mvi.model

internal sealed interface ItunesSideEffect {

    sealed interface Ui : ItunesSideEffect {
        data object Back : Ui
    }

    sealed interface Domain : ItunesSideEffect {
        data class LoadData(val offset: Int, val limit: Int, val term: String) : Domain
    }
}


