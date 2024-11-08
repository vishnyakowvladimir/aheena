package com.example.data_source_impl.model.itunes

class ItunesTracksInfoDto(
    val resultCount: Int?,
    val results: List<ItunesTrack>?,
) {

    class ItunesTrack(
        val trackName: String?,
    )
}
