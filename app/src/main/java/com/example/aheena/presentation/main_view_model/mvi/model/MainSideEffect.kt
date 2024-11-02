package com.example.aheena.presentation.main_view_model.mvi.model

import com.example.domain_models.theme.ViewScaleDomain

internal sealed interface MainSideEffect {

    sealed interface Ui : MainSideEffect {
        data object Back : Ui
        data object ApplyTheme : Ui
        data object OpenAuthentication : Ui
    }

    sealed interface Domain : MainSideEffect {
        data object LoadData : Domain
        data class ChangeViewScale(val viewScaleDomain: ViewScaleDomain) : Domain
        data class SaveUserActivity(val millis: Long) : Domain
    }
}


