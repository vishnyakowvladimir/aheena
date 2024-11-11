package com.example.data_source_impl.repository.itunes

import com.example.core.network.model.ApiResult
import com.example.core.network.utils.sendRequest
import com.example.data_source_api.repository.itunes.ItunesRepository
import com.example.data_source_impl.api.ItunesApi
import com.example.data_source_impl.mapper.itunes.ItunesMapper
import com.example.domain_models.itunes.ItunesTrack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val api: ItunesApi,
    private val mapper: ItunesMapper,
) : ItunesRepository {

    override fun loadTracks(offset: Int, limit: Int, term: String): Flow<ApiResult<List<ItunesTrack>>> {
        return sendRequest(mapper = mapper::map) {
            api.loadItunesTrackList(
                offset = offset,
                limit = limit,
                term = term,
            )
        }
    }
}
