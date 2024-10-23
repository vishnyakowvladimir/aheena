package com.example.aheena.presentation.main_view_model.mvi.model

internal sealed interface MainEvent {

    sealed interface Ui : MainEvent {
        data object None : Ui
        data object OnBackPressed : Ui
    }

    sealed interface Domain : MainEvent {
        data object OnLoadDataNeeded : Domain
        data object OnDataLoaded : Domain
    }
}
