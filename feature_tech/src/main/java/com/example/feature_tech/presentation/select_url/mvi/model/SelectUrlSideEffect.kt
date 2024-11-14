package com.example.feature_tech.presentation.select_url.mvi.model

import com.example.data_source_api.models.network.UrlsType

internal sealed interface SelectUrlSideEffect {

    sealed interface Ui : SelectUrlSideEffect {
        data object Back : Ui
    }

    sealed interface Domain : SelectUrlSideEffect {
        data class SaveUrlsType(val urlsType: UrlsType) : Domain
    }
}


