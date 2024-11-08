package com.example.feature_itunes.presentation.itunes.mvi.model

import com.example.domain_models.itunes.ItunesTrack

internal data class ItunesDomainState(
    val isError: Boolean = false,
    val isLoading: Boolean = true,
    val tracks: List<ItunesTrack> = emptyList(),
)
