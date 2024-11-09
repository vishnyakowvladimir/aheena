package com.example.data_sdk_impl.interactor.interactor

import com.example.core.network.model.ApiResult
import com.example.data_sdk_api.interactor.itunes.ItunesInteractor
import com.example.data_source_api.repository.itunes.ItunesRepository
import com.example.domain_models.itunes.ItunesTrack
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ItunesInteractorImpl @Inject constructor(
    private val itunesRepository: ItunesRepository
) : ItunesInteractor {

    override fun loadTracks(offset: Int, limit: Int, term: String): Flow<ApiResult<List<ItunesTrack>>> {
        return itunesRepository.loadTracks(
            offset = offset,
            limit = limit,
            term = term,
        )
    }
}
