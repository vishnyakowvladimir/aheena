package com.example.aheena.presentation.main_activity.mvi.model

import android.net.Uri
import com.example.domain_models.theme.ViewScaleDomain

internal sealed interface MainSideEffect {

    sealed interface Ui : MainSideEffect {
        data object Back : Ui
        data object ApplyTheme : Ui
        data object OpenAuthentication : Ui
        data class HandleDeeplink(val uri: Uri) : Ui
    }

    sealed interface Domain : MainSideEffect {
        data object LoadData : Domain
        data class ChangeViewScale(val viewScaleDomain: ViewScaleDomain) : Domain
        data class SaveUserActivity(val millis: Long) : Domain
    }
}


