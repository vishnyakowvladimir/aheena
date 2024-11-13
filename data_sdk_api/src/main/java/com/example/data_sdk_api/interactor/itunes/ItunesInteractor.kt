package com.example.data_sdk_api.interactor.itunes

import com.example.core_api.network.model.ApiResult
import com.example.domain_models.itunes.ItunesTrack
import kotlinx.coroutines.flow.Flow

interface ItunesInteractor {

    fun loadTracks(offset: Int, limit: Int, term: String): Flow<ApiResult<List<ItunesTrack>>>
}
