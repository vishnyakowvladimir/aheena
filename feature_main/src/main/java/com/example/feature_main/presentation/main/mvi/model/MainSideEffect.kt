package com.example.feature_main.presentation.main.mvi.model

import com.example.core_api.pending_navigation.model.PendingNavigationState

internal sealed interface MainSideEffect {

    sealed interface Ui : MainSideEffect {
        data object Back : Ui
        data class PendingNavigationAction(val state: PendingNavigationState.Destination) : Ui
    }

    sealed interface Domain : MainSideEffect
}


