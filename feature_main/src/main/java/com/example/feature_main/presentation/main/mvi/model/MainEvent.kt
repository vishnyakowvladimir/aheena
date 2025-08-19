package com.example.feature_main.presentation.main.mvi.model

import com.example.core_api.pending_navigation.model.PendingNavigationState

internal sealed interface MainEvent {

    data object None : MainEvent

    sealed interface Ui : MainEvent {
        data object OnBackPressed : Ui
        data class OnBottomBarItemClick(val index: Int) : Ui
        data class OnPendingNavigation(val state: PendingNavigationState) : Ui
    }

    sealed interface Domain : MainEvent
}
