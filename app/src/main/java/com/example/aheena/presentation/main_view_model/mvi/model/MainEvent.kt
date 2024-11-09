package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.domain_models.theme.ViewScaleDomain

internal sealed interface MainEvent {

    sealed interface Ui : MainEvent {
        data object None : Ui
        data object OnBackPressed : Ui
        data object OnApplyThemeNeeded : Ui
        data object OnThemeApplied : Ui
        data class OnChangeScaleNeeded(val scale: ViewScaleDomain) : Ui
        data object OnTouch : Ui
    }

    sealed interface Domain : MainEvent {
        data object None : Domain
        data object OnLoadDataNeeded : Domain
        data object OnDataLoaded : Domain
        data class OnScaleChanged(val scale: ViewScaleDomain) : Domain
        data object OnNoInternetConnection : Domain
    }
}
