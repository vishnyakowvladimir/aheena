package com.example.feature_tech.presentation.select_url.mvi.model

import com.example.data_source_api.models.network.UrlsType

internal sealed interface SelectUrlEvent {

    data object None : SelectUrlEvent

    sealed interface Ui : SelectUrlEvent {
        data object OnBackPressed : Ui
        data class OnUrlsTypeClick(val urlsType: UrlsType) : Ui
    }

    sealed interface Domain : SelectUrlEvent {}
}
