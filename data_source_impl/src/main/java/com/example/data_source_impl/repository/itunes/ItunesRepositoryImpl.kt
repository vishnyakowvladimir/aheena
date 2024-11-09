package com.example.data_source_impl.repository.itunes

import com.example.core.network.model.ApiResult
import com.example.core.network.model.mapResultSuccess
import com.example.core.network.model.toApiResult
import com.example.core.network.model.toResultError
import com.example.data_source_api.repository.itunes.ItunesRepository
import com.example.data_source_impl.api.ItunesApi
import com.example.data_source_impl.mapper.itunes.ItunesMapper
import com.example.domain_models.itunes.ItunesTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val api: ItunesApi,
    private val mapper: ItunesMapper,
) : ItunesRepository {

    override fun loadTracks(offset: Int, limit: Int, term: String): Flow<ApiResult<List<ItunesTrack>>> {
        return flow {
            val result = api.loadItunesTrackList(
                offset = offset,
                limit = limit,
                term = term,
            )
                .toApiResult()
                .mapResultSuccess(mapper::map)

            emit(result)
        }
            .catch { throwable ->
                emit(throwable.toResultError())
            }
    }
}
