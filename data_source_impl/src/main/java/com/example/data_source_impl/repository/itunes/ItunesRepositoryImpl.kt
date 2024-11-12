package com.example.data_source_impl.repository.itunes

import com.example.core.cache.data_source.CacheDataSource
import com.example.core.cache.model.CachedDataKey
import com.example.core.network.model.ApiResult
import com.example.core.network.model.mapDtoToDomain
import com.example.data_source_api.repository.itunes.ItunesRepository
import com.example.data_source_impl.api.ItunesApi
import com.example.data_source_impl.mapper.itunes.ItunesMapper
import com.example.domain_models.itunes.ItunesTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ItunesRepositoryImpl @Inject constructor(
    private val cacheDataSource: CacheDataSource,
    private val api: ItunesApi,
    private val mapper: ItunesMapper,
) : ItunesRepository {

    override fun loadTracks(offset: Int, limit: Int, term: String): Flow<ApiResult<List<ItunesTrack>>> {

//        просто запрос, если не надо кэшировать
//        return sendRequest {
//            api.loadItunesTrackList(
//                offset = offset,
//                limit = limit,
//                term = term,
//            )
//        }
//            .mapDtoToDomain(mapper::map)
//            .flowOn(Dispatchers.IO)

        return cacheDataSource.get(
            key = CachedDataKey.ITUNES,
            postfixKey = "$term $offset $limit",
        ) {
            api.loadItunesTrackList(
                offset = offset,
                limit = limit,
                term = term,
            )
        }
            .mapDtoToDomain(mapper::map)
            .flowOn(Dispatchers.IO)
    }
}
