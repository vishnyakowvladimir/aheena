package com.example.aheena.presentation.main_view_model.mvi.model

internal sealed interface MainSideEffect {

    sealed interface Ui : MainSideEffect {
        data object Back : Ui
        data object OpenAuthentication : Ui
    }

    sealed interface Domain : MainSideEffect {
        data object LoadAppTheme : Domain
    }
}


