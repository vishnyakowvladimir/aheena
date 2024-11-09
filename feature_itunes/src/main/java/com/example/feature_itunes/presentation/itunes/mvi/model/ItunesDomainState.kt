package com.example.feature_itunes.presentation.itunes.mvi.model

import com.example.domain_models.itunes.ItunesTrack

internal data class ItunesDomainState(
    val isError: Boolean = false,
    val isTracksLoading: Boolean = false,   // когда идет процесс загрузки треков
    val showLoading: Boolean = true,   // показывать на экране загрузку или нет
    val isAllLoaded: Boolean = false,
    val page: Int = 0,
    val tracks: List<ItunesTrack> = emptyList(),
)
