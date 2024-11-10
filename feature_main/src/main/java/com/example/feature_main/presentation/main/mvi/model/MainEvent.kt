package com.example.feature_main.presentation.main.mvi.model

internal sealed interface MainEvent {

    data object None : MainEvent

    sealed interface Ui : MainEvent {
        data object OnBackPressed : Ui
        data class OnBottomBarItemClick(val index: Int) : Ui
    }

    sealed interface Domain : MainEvent
}
