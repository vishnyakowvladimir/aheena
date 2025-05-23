package com.example.data_source_api.repository.itunes

import com.example.core_api.network.model.ApiResult
import com.example.domain_models.itunes.ItunesTrack
import kotlinx.coroutines.flow.Flow

interface ItunesRepository {

    fun loadTracks(offset: Int, limit: Int, term: String): Flow<ApiResult<List<ItunesTrack>>>
}
