package com.example.feature_itunes.presentation.itunes.mapper

import com.example.feature_itunes.presentation.itunes.model.ItunesUiState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesDomainState
import javax.inject.Inject

internal class ItunesMapper @Inject constructor() {

    fun map(domainState: ItunesDomainState): ItunesUiState {
        return ItunesUiState(
            isLoading = domainState.isLoading,
        )
    }
}
