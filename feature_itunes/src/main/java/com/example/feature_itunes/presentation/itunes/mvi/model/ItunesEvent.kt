package com.example.feature_itunes.presentation.itunes.mvi.model

import com.example.domain_models.itunes.ItunesTrack

internal sealed interface ItunesEvent {

    data object None : ItunesEvent

    sealed interface Ui : ItunesEvent {
        data object OnBackPressed : Ui
    }

    sealed interface Domain : ItunesEvent {
        data object LoadDataNeeded : Domain
        data class OnDataLoaded(val tracks: List<ItunesTrack>): Domain
    }
}
