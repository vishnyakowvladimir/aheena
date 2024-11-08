package com.example.data_source_impl.mapper.itunes

import com.example.data_source_impl.model.itunes.ItunesTracksInfoDto
import com.example.domain_models.itunes.ItunesTrack
import javax.inject.Inject

class ItunesMapper @Inject constructor() {

    fun map(infoDto: ItunesTracksInfoDto): List<ItunesTrack> {
        return infoDto.results?.map { track ->
            ItunesTrack(
                name = track.trackName.orEmpty(),
            )
        }.orEmpty()
    }
}
