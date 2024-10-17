package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.aheena.presentation.main_view_model.mvi.ThemeData

internal sealed interface MainEvent {

    sealed interface Ui : MainEvent {
        data object OnBackPressed : Ui
    }

    sealed interface Domain : MainEvent {
        data object OnLoadAppThemeNeeded : Domain
        data class OnAppThemeLoaded(val data: ThemeData): Domain
    }
}
