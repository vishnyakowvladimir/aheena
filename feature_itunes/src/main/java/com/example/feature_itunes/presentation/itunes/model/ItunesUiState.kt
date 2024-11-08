package com.example.feature_itunes.presentation.itunes.model

import com.example.domain_models.itunes.ItunesTrack
import kotlinx.collections.immutable.ImmutableList

internal data class ItunesUiState(
    val data: ItunesData,
)

internal sealed interface ItunesData {

    data object Loading : ItunesData

    data object Error : ItunesData

    data class Data(
        val tracks: ImmutableList<ItunesTrack>,
    ) : ItunesData
}
