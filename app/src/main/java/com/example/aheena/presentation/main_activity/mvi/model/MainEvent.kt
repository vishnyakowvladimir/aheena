package com.example.aheena.presentation.main_activity.mvi.model

import android.net.Uri
import com.example.domain_models.theme.ViewScaleDomain

internal sealed interface MainEvent {

    data object None : MainEvent

    sealed interface Ui : MainEvent {
        data object OnBackPressed : Ui
        data object OnApplyThemeNeeded : Ui
        data object OnThemeApplied : Ui
        data class OnChangeScaleNeeded(val scale: ViewScaleDomain) : Ui
        data object OnTouch : Ui
        data class OnDeeplink(val uri: Uri) : Ui
    }

    sealed interface Domain : MainEvent {
        data object OnLoadDataNeeded : Domain
        data object OnDataLoaded : Domain
        data class OnScaleChanged(val scale: ViewScaleDomain) : Domain
        data object OnNoInternetConnection : Domain
    }
}
