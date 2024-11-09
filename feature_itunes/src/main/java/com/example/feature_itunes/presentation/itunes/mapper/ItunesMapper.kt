package com.example.feature_itunes.presentation.itunes.mapper

import com.example.feature_itunes.presentation.itunes.model.ItunesData
import com.example.feature_itunes.presentation.itunes.model.ItunesUiState
import com.example.feature_itunes.presentation.itunes.mvi.model.ItunesDomainState
import kotlinx.collections.immutable.toImmutableList
import javax.inject.Inject

internal class ItunesMapper @Inject constructor() {

    fun map(domainState: ItunesDomainState): ItunesUiState {
        val data = when {
            domainState.isError -> {
                ItunesData.Error
            }

            domainState.showLoading -> {
                ItunesData.Loading
            }

            else -> {
                ItunesData.Data(
                    tracks = domainState.tracks.toImmutableList(),
                )
            }
        }

        return ItunesUiState(
            data = data,
        )
    }
}
