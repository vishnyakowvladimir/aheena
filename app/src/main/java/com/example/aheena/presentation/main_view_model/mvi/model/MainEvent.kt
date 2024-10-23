package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.domain_models.theme.ViewScaleDomain

internal sealed interface MainEvent {

    sealed interface Ui : MainEvent {
        data object None : Ui
        data object OnBackPressed : Ui
        data object OnApplyThemeNeeded : Ui
        data class OnChangeScaleNeeded(val scale: ViewScaleDomain) : Ui
    }

    sealed interface Domain : MainEvent {
        data object OnLoadDataNeeded : Domain
        data object OnDataLoaded : Domain
        data class OnScaleChanged(val scale: ViewScaleDomain) : Domain
    }
}
